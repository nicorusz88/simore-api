package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "vital_measurement")
public class VitalMeasurement extends BaseEntity {

    @ManyToOne
    private Vital vital;

    private Date date;

    private Long value1;
    private Long value2;

    public Vital getVital() {
        return vital;
    }

    public void setVital(Vital vital) {
        this.vital = vital;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getValue1() {
        return value1;
    }

    public void setValue1(Long value1) {
        this.value1 = value1;
    }

    public Long getValue2() {
        return value2;
    }

    public void setValue2(Long value2) {
        this.value2 = value2;
    }
}
