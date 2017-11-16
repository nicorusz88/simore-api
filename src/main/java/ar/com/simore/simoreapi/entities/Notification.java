package ar.com.simore.simoreapi.entities;

import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private NotificationTypeEnum notificationType;

    @ManyToOne
    private User user;

    /**
     * It is the ID of the represented notification type,
     * example it is the ID of the medication for which this notification is created
     */
    private long referenceID;

    /**
     * indicates if the notification has been read or not
     */
    private boolean isRead;

    /**
     * Indicates the date that the notification was read
     */
    private Date readDate;

    /**
     * This is the date the notifications should be sent
     */
    private Date expecetedSendDate;

    /**
     * This is the date the notification has been sent
     */
    private Date actualSendDate;

    /**
     * Notification Title to be sghonw to the final user
     */
    private String title;

    /**
     * Notification body to be shown to the final user
     */
    private String body;

    public NotificationTypeEnum getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationTypeEnum notificationType) {
        this.notificationType = notificationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(long referenceID) {
        this.referenceID = referenceID;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public Date getExpecetedSendDate() {
        return expecetedSendDate;
    }

    public void setExpecetedSendDate(Date expecetedSendDate) {
        this.expecetedSendDate = expecetedSendDate;
    }

    public Date getActualSendDate() {
        return actualSendDate;
    }

    public void setActualSendDate(Date actualSendDate) {
        this.actualSendDate = actualSendDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationType=" + notificationType +
                ", user=" + user +
                ", referenceID=" + referenceID +
                ", isRead=" + isRead +
                ", readDate=" + readDate +
                ", expecetedSendDate=" + expecetedSendDate +
                ", actualSendDate=" + actualSendDate +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
