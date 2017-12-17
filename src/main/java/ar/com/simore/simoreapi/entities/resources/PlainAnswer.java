package ar.com.simore.simoreapi.entities.resources;

/**
 * Used to receive the answer to the questions. It could be either the choice questuion id
 * or the plain text answer
 */
public class PlainAnswer {

    private String answer;


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
