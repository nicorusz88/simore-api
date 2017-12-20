package ar.com.simore.simoreapi.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "treatment")
public class Treatment extends BaseEntity {

    private Date createdAt;

    @ManyToOne
    private TreatmentTemplate treatmentTemplate;

    @Size(max = 500)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Vital> vitals;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "treatment")
    private VitalsSynchronization vitalsSynchronization;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Medication> medications;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;


    @OneToMany(cascade = CascadeType.ALL)
    private List<CheckIn> checkIns;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CheckInResult> checkInsResults;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Alert> alerts;

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public VitalsSynchronization getVitalsSynchronization() {
        return vitalsSynchronization;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setVitalsSynchronization(VitalsSynchronization vitalsSynchronization) {
        this.vitalsSynchronization = vitalsSynchronization;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Vital> getVitals() {
        return vitals;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TreatmentTemplate getTreatmentTemplate() {
        return treatmentTemplate;
    }

    public void setTreatmentTemplate(TreatmentTemplate treatmentTemplate) {
        this.treatmentTemplate = treatmentTemplate;
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

    public List<CheckInResult> getCheckInsResults() {
        return checkInsResults;
    }

    public void setCheckInsResults(List<CheckInResult> checkInsResults) {
        this.checkInsResults = checkInsResults;
    }

}
