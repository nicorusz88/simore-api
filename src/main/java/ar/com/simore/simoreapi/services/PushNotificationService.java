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
import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

    private final Logger logger = Logger.getLogger("notifications");

    @Value("${firebase.api.key}")
    private String apiKey;

    @Value("${firebase.api.url}")
    private String firebaseAPIURL;

    @Autowired
    private NotificationService notificationService;

    public boolean sendNotification(final Notification notification) {
        final JSONObject body = new JSONObject();
        body.put("to", notification.getUser().getDeviceToken());

        JSONObject notificationData = new JSONObject();
        notificationData.put("title", notification.getTitle());
        notificationData.put("body", notification.getBody());

        final JSONObject data = new JSONObject();
        data.put("type", notification.getNotificationType().name());
        data.put("notificationId", notification.getId());
        data.put("referenceId", notification.getReferenceId());

        body.put("notification", notificationData);
        body.put("data", data);

        logger.info(String.format("Sending notification '%s' to user '%s'. Notification JSON is \n %s", notification.getTitle(), notification.getUser().getUserName(), body.toString()));

        /**
         {
         "notification": {
         "title": "Take your medication",
         "body": "Remember to take 20 Mg of Axalan"
         },
         "data": {
         "type": "MEDICATION",
         },
         "to": "32f3f23f3g4h2j4242h4h24h",
         "priority": "high"
         }
         */
        HttpEntity<String> request = new HttpEntity<>(body.toString());

        try {
            CompletableFuture<String> pushNotification = send(request);
            CompletableFuture.allOf(pushNotification).join();
            final String firebaseResponse = pushNotification.get();
            logger.info("FireBase Response: " + firebaseResponse);
            //TODO: Read response and set to notification
            return true;
        } catch (Exception e) {
           logger.error("An exception ocurred when sending the notifications", e);
        }
        return false;
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
