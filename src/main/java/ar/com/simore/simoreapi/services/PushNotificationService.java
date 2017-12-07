package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.interceptors.HeaderRequestInterceptor;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class PushNotificationService {

    private static final String TO = "to";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String TYPE = "type";
    private static final String NOTIFICATION_ID = "notificationId";
    private static final String REFERENCE_ID = "referenceId";
    private static final String NOTIFICATION = "notification";
    private static final String DATA = "data";
    private final Logger logger = Logger.getLogger("notifications");

    @Value("${firebase.api.key}")
    private String apiKey;

    @Value("${firebase.api.url}")
    private String firebaseAPIURL;

    @Autowired
    private NotificationService notificationService;

    public String sendNotification(final Notification notification) {
        final JSONObject body = createJsonObject(notification);
        final HttpEntity<String> request = new HttpEntity<>(body.toString());
        try {
            CompletableFuture<String> pushNotification = send(request);
            CompletableFuture.allOf(pushNotification).join();
            return pushNotification.get();
        } catch (Exception e) {
           logger.error("An exception ocurred when sending the notifications", e);
        }
        return null;
    }

    /**Creates something like the following:
     *
     /**
     {
        "notification": {
            "title": "Take your medication",
            "body": "Remember to take 20 Mg of Axalan"
         },
        "data": {
            "type": "MEDICATION",
            "reference_id": "3"
        },
        "to": "32f3f23f3g4h2j4242h4h24h"
     }
     * @param notification
     * @return
     */
    private JSONObject createJsonObject(Notification notification) {
        final JSONObject body = new JSONObject();
        body.put(TO, notification.getUser().getDeviceToken());

        JSONObject notificationData = new JSONObject();
        notificationData.put(TITLE, notification.getTitle());
        notificationData.put(BODY, notification.getBody());

        final JSONObject data = new JSONObject();
        data.put(TYPE, notification.getNotificationType().name());
        data.put(NOTIFICATION_ID, notification.getId());
        data.put(REFERENCE_ID, notification.getReferenceId());

        body.put(NOTIFICATION, notificationData);
        body.put(DATA, data);

        logger.info(String.format("Sending notification '%s' to user '%s'. Notification JSON is \n %s", notification.getTitle(), notification.getUser().getUserName(), body.toString()));
        return body;
    }


    @Async
    private CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + apiKey));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(firebaseAPIURL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}
