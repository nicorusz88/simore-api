package ar.com.simore.simoreapi.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "choice_question")
@JsonDeserialize(as = ChoiceQuestion.class)
public class ChoiceQuestion extends Question{

    @OneToMany(cascade= CascadeType.ALL)
    private List<ChoiceQuestionOption> choiceQuestionOptions;

    public List<ChoiceQuestionOption> getChoiceQuestionOptions() {
        return choiceQuestionOptions;
    }

    public void setChoiceQuestionOptions(List<ChoiceQuestionOption> choiceQuestionOptions) {
        this.choiceQuestionOptions = choiceQuestionOptions;
    }

}
