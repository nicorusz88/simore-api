package ar.com.simore.simoreapi.entities;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Represents an activity that can be done, marking it as checked and a date where it was completed (or not).
 * This will be used for appointments and recommendation
 */
@MappedSuperclass
public class Doable extends BaseEntity {

    private boolean done = false;

    private Date doneDate;

    private Date notificationDate;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }


    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }
}
