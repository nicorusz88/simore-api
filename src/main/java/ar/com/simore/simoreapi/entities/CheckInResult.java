package ar.com.simore.simoreapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "checkin_result")
public class CheckInResult extends BaseEntity{

    @ManyToOne
    private CheckIn checkIn;

    @OneToOne(cascade = CascadeType.ALL)
    private Answer answer;

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
