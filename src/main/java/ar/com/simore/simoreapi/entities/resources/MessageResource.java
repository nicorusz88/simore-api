package ar.com.simore.simoreapi.entities.resources;

import java.util.Date;


public class MessageResource {

    private Date sendDate;

    private UserResource from;

    private UserResource to;

    private String text;

    private boolean isRead;

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public UserResource getFrom() {
        return from;
    }

    public void setFrom(UserResource from) {
        this.from = from;
    }

    public UserResource getTo() {
        return to;
    }

    public void setTo(UserResource to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}

