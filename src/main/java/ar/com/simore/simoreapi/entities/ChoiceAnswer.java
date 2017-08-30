package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "choice_answer")
public class ChoiceAnswer extends Answer {

    @ManyToOne
    private ChoiceQuestionOption choiceQuestionOption;

    public ChoiceQuestionOption getChoiceQuestionOption() {
        return choiceQuestionOption;
    }

    public void setChoiceQuestionOption(ChoiceQuestionOption choiceQuestionOption) {
        this.choiceQuestionOption = choiceQuestionOption;
    }
}
