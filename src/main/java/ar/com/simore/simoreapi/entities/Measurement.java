package ar.com.simore.simoreapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType= DiscriminatorType.STRING)
@Table(name = "measurement")
@DiscriminatorValue(value="measurement")
@DiscriminatorOptions(force=true)
public class Measurement extends BaseEntity{

    @ManyToOne
    @JsonIgnore
    private VitalMeasurement vitalMeasurement;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public VitalMeasurement getVitalMeasurement() {
        return vitalMeasurement;
    }

    public void setVitalMeasurement(VitalMeasurement vitalMeasurement) {
        this.vitalMeasurement = vitalMeasurement;
    }
}
