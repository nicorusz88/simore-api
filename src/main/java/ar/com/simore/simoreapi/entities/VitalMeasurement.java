package ar.com.simore.simoreapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "vitalMeasurement", orphanRemoval=true)
    private List<Measurement> measurements;

    @ManyToOne
    @JsonIgnore
    private VitalsSynchronization vitalsSynchronization;

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

    public VitalsSynchronization getVitalsSynchronization() {
        return vitalsSynchronization;
    }

    public void setVitalsSynchronization(VitalsSynchronization vitalsSynchronization) {
        this.vitalsSynchronization = vitalsSynchronization;
    }
}
