package ar.com.simore.simoreapi.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "open_question")
@JsonDeserialize(as = OpenQuestion.class)
public class OpenQuestion extends Question{

}
