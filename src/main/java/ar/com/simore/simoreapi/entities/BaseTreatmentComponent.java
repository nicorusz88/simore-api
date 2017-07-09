package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;

@Entity
public class BaseTreatmentComponent extends BaseEntity{

    private String name;

    public String getText() {
        return name;
    }

    public void setText(String text) {
        this.name = text;
    }
}
