package ar.com.simore.simoreapi.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "checkin")
public class CheckIn extends BaseTreatmentComponent {

    /**
     * Frecuency in to ask for the survey in hours
     */
    private int frecuency;

    @OneToOne(cascade= CascadeType.ALL)
    private Question question;

    public int getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(int frecuency) {
        this.frecuency = frecuency;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
