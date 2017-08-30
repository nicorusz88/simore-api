package ar.com.simore.simoreapi.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "treatment_template")
public class TreatmentTemplate extends BaseEntity {
    @Size(max = 100)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Vital> vitals;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Medication> medications;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CheckIn> checkIns;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    public List<Vital> getVitals() {
        return vitals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVitals(List<Vital> vitals) {
        this.vitals = vitals;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public List<CheckIn> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(List<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
