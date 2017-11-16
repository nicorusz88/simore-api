package ar.com.simore.simoreapi.entities.websocket;

public class NotificationMessage {

	private String name;

	public NotificationMessage(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
