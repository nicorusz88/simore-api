package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.websocket.NotificationMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationsWebSocketController {

	@MessageMapping("/notifications") //Listens to messages "NotificationMessage" sent to /app/notifications
	@SendTo("/topic/notifications") //Sends back the response to this queue
	public NotificationMessage notifications(NotificationMessage notificationMessage) throws Exception {
		Thread.sleep(3000); // simulated delay
		return new NotificationMessage("Hello, " + notificationMessage.getName() + "!");
	}
}