package ar.com.simore.simoreapi.entities;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Holds the information for vitals measurements synchronized data.
 */

@Entity
@Table(name = "vitals_synchronization")
public class VitalsSynchronization extends BaseEntity {

    @OneToOne
    @JoinColumn (name="treatment")
    private Treatment treatment;

    /**
     * Last date of synchronization
     */
    private Date lastSuccessfulSync;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "vitalsSynchronization")
    @Fetch(FetchMode.SUBSELECT)
    private List<VitalMeasurement> vitalsMeasurements;


    public Date getLastSuccessfulSync() {
        return lastSuccessfulSync;
    }

    public void setLastSuccessfulSync(Date lastSuccessfulSync) {
        this.lastSuccessfulSync = lastSuccessfulSync;
    }



    public List<VitalMeasurement> getVitalsMeasurements() {
        return vitalsMeasurements;
    }

    public void setVitalsMeasurements(List<VitalMeasurement> vitalsMeasurements) {
        this.vitalsMeasurements = vitalsMeasurements;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
}
