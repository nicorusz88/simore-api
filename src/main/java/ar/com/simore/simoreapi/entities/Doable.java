package ar.com.simore.simoreapi.entities;

import java.util.Date;

/**
 * Represents an acivity that can be done, marking it as checked and a date where it was completed (or not).
 * This can be used, for example for an appointment, for a Check in, for a recommendation, etc
 */
public class Doable extends BaseEntity {

    private boolean done;

    private Date date;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
