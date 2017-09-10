package ar.com.simore.simoreapi.entities;

import javax.persistence.*;
import java.util.List;

/**
 * It holds all the information related to a specific vital.
 * Ex. It holds all the BPMs for the heart rate.
 */
@Entity
@Table(name = "vital_measurement")
public class VitalMeasurement extends BaseEntity {

    @ManyToOne
    private Vital vital;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Measurement> measurements;


    public Vital getVital() {
        return vital;
    }

    public void setVital(Vital vital) {
        this.vital = vital;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }
}
