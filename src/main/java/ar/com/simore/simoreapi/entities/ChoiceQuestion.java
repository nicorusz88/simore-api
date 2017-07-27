package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "choice_question")
public class ChoiceQuestion extends BaseEntity implements Question{


    @OneToMany
    private List<ChoiceQuestionOption> choiceQuestionOptions;


    public List<ChoiceQuestionOption> getChoiceQuestionOptions() {
        return choiceQuestionOptions;
    }

    public void setChoiceQuestionOptions(List<ChoiceQuestionOption> choiceQuestionOptions) {
        this.choiceQuestionOptions = choiceQuestionOptions;
    }

}
