package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "open_answer")
public class OpenAnswer extends Answer {

    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
