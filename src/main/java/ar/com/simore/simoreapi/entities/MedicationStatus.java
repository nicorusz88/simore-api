package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "medication_status")
public class MedicationStatus extends Doable {

    @ManyToOne
    private Medication medication;

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
