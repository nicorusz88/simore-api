package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.websocket.Greeting;
import ar.com.simore.simoreapi.entities.websocket.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	@MessageMapping("/hello") //Listens to messages ""HelloMessage" sent to /app/hello
	@SendTo("/topic/alertas") //Sends back the response to this queue
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(3000); // simulated delay
		return new Greeting("Hello, " + message.getName() + "!");
	}
}