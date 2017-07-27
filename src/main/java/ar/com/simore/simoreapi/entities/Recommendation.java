package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "recommendation")
public class Recommendation extends BaseTreatmentComponent{

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
