package ar.com.simore.simoreapi.entities.resources;

import java.util.Date;

/**
 * Object sent as response for showing medications
 */
public class MedicationResource {

    private String name;
    private String quantity;
    private Date previous;
    private Date next;

    public MedicationResource(String name, String quantity, Date previous, Date next) {
        this.name = name;
        this.quantity = quantity;
        this.previous = previous;
        this.next = next;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getPrevious() {
        return previous;
    }

    public void setPrevious(Date previous) {
        this.previous = previous;
    }

    public Date getNext() {
        return next;
    }

    public void setNext(Date next) {
        this.next = next;
    }
}
