package ar.com.simore.simoreapi.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "checkin_result")
public class CheckInResult extends BaseEntity{

    @ManyToOne
    private CheckIn checkIn;

    @OneToOne(cascade = CascadeType.ALL)
    private Answer answer;

    private Date answeredDate;

    public Date getAnsweredDate() {
        return answeredDate;
    }

    public void setAnsweredDate(Date answeredDate) {
        this.answeredDate = answeredDate;
    }

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
