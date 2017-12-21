package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Entity that represents messages between  users and professionals
 */
@Entity
@Table(name = "message")
public class Message extends BaseEntity{

    /**
     * Send Date
     */
    private Date sendDate;

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    private String text;

    private boolean isRead;

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
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

    @Override
    public String toString() {
        return "Message{" +
                "sendDate=" + sendDate +
                ", from=" + from +
                ", to=" + to +
                ", text='" + text + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}

