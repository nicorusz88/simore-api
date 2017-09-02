package ar.com.simore.simoreapi.entities;

import ar.com.simore.simoreapi.entities.utils.QuestionDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonDeserialize(using = QuestionDeserializer.class)
public abstract class Question extends BaseEntity {

    @NotNull
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
