package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;

@Entity
public class CheckIn extends BaseTreatmentComponent {

    private String text;

    /**
     * Frecuency in  hours
     */
    private int frecuency;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(int frecuency) {
        this.frecuency = frecuency;
    }
}
