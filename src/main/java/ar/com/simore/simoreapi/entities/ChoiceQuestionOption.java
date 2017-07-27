package ar.com.simore.simoreapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "choice_question_option")
public class ChoiceQuestionOption extends BaseEntity{

    @Column(name = "[option]", nullable = false)
    private String option;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
