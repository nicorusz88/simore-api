package ar.com.simore.simoreapi.entities.utils;

import ar.com.simore.simoreapi.entities.ChoiceQuestion;
import ar.com.simore.simoreapi.entities.OpenQuestion;
import ar.com.simore.simoreapi.entities.Question;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

/**
 * As Question is an Abtract class, jackson needs some help deciding the concrete cclass to convert
 */
public class QuestionDeserializer extends JsonDeserializer<Question> {

    public static final String CHOICE_QUESTION_OPTIONS = "choiceQuestionOptions";

    @Override
    public Question deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = mapper.readTree(jp);
        Class<? extends Question> questionClass;
        if (checkConditionsForChoiceQuestion(root)) {
            questionClass = ChoiceQuestion.class;
        } else {
            questionClass = OpenQuestion.class;
        }
        return mapper.convertValue(root, questionClass);
    }

    private boolean checkConditionsForChoiceQuestion(ObjectNode root) {
        if(root.get(CHOICE_QUESTION_OPTIONS) !=null){
            return true;
        }
        return false;
    }
}
