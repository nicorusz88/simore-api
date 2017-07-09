package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;

@Entity
public class Medication extends BaseTreatmentComponent{

    private String quantity;
    /**
     * Frecuency in  hours
     */
    private int frecuency;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(int frecuency) {
        this.frecuency = frecuency;
    }
}
