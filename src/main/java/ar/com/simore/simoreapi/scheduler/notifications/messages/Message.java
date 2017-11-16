package ar.com.simore.simoreapi.scheduler.notifications.messages;

/**
 * It represents a messaage that will be sent as a JSON response using web sockets with STOMP.
 */
public class Message {

    private String fromUserId;
    private String toUserId;
    private String text;

    public Message(String fromUserId, String toUserId, String text) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.text = text;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "fromUserId='" + fromUserId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
