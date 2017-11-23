package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.interceptors.HeaderRequestInterceptor;
import org.json.simple.JSONObject;
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

    @Value("${firebase.api.key}")
    private String apiKey;

    @Value("${firebase.api.url}")
    private String firebaseAPIURL;

    public void sendNotification(final Notification notification) {
        JSONObject body = new JSONObject();
        body.put("to", notification.getUser().getDeviceToken());
        body.put("priority", "high");

        JSONObject notificationData = new JSONObject();
        notificationData.put("title", "JSA Notification");
        notificationData.put("body", "Happy Message!");

        JSONObject data = new JSONObject();
        data.put("Key-1", "JSA Data 1");
        data.put("Key-2", "JSA Data 2");

        body.put("notification", notificationData);
        body.put("data", data);

        /**
         {
         "notification": {
         "title": "JSA Notification",
         "body": "Happy Message!"
         },
         "data": {
         "Key-1": "JSA Data 1",
         "Key-2": "JSA Data 2"
         },
         "to": "/topics/JavaSampleApproach",
         "priority": "high"
         }
         */
        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            //TODO: Read response and set to notification
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + apiKey));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(firebaseAPIURL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }


}
