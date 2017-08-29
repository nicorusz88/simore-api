package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "recommendation")
public class Recommendation extends BaseTreatmentComponent{

    private String text;

    public String getName() {
        return text;
    }

    public void setName(String text) {
        this.text = text;
    }
}
