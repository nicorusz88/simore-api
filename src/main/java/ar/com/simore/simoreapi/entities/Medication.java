package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "medication")
public class Medication extends BaseTreatmentComponent{

    /**
     * Quantity to take the drug in Mg
     */
    private long quantity;
    /**
     * Frecuency to take the drug in hours
     */
    private long frecuency;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(long frecuency) {
        this.frecuency = frecuency;
    }
}
