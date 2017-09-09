package ar.com.simore.simoreapi.entities;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Holds the information for vitals measurements synchronized data.
 */

@Entity
@Table(name = "vitals_synchronization")
public class VitalsSynchronization extends BaseEntity {

    /**
     * Last date of synchronization
     */
    private Date lastSync;
    /**
     * Indicates if the synchronization was sucessfull or not
     */
    private boolean wasSuccess;

    @OneToMany(cascade = CascadeType.ALL)
    private List<VitalMeasurement> vitalsMeasurements;


    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }

    public boolean isWasSuccess() {
        return wasSuccess;
    }

    public void setWasSuccess(boolean wasSuccess) {
        this.wasSuccess = wasSuccess;
    }


    public List<VitalMeasurement> getVitalsMeasurements() {
        return vitalsMeasurements;
    }

    public void setVitalsMeasurements(List<VitalMeasurement> vitalsMeasurements) {
        this.vitalsMeasurements = vitalsMeasurements;
    }
}
