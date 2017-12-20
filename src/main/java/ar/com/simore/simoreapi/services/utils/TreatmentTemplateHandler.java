package ar.com.simore.simoreapi.services.utils;

import ar.com.simore.simoreapi.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to copy all treatment components from the template to the actual treatment ffor the pacient
 */
public class TreatmentTemplateHandler {


    /** Copies all the treatmeent components from the template to the actual treatment of the pacient
     * @param treatmentTemplate Template
     * @param treatment Actual template of the pacient being created
     * @return
     */
    public static Treatment copyFromTemplate(TreatmentTemplate treatmentTemplate, Treatment treatment) {
        treatment.setVitals(new ArrayList<>(copyVitals(treatmentTemplate.getVitals())));
        treatment.setAppointments(new ArrayList<>(copyAppointments(treatmentTemplate.getAppointments())));
        treatment.setCheckIns(new ArrayList<>(copyCheckins(treatmentTemplate.getCheckIns())));
        treatment.setMedications(new ArrayList<>(copyMedications(treatmentTemplate.getMedications())));
        treatment.setRecommendations(new ArrayList<>(copyRecommendations(treatmentTemplate.getRecommendations())));
        treatment.setTreatmentTemplate(treatmentTemplate);
        return treatment;
    }

    private static List<Vital> copyVitals(List<Vital> vitals) {
        List<Vital> newVitals = new ArrayList<>();
        vitals.forEach(v -> {
            Vital vital = new Vital();
            vital.setType(v.getType());
            vital.setWearableType(v.getWearableType());
            vital.setName(v.getName());
            newVitals.add(vital);
        });
        return newVitals;
    }

    private static List<Appointment> copyAppointments(List<Appointment> appointments) {
        List<Appointment> newAppointments = new ArrayList<>();
        appointments.forEach(a -> {
            Appointment appointment = new Appointment();
            appointment.setAddress(a.getAddress());
            appointment.setDate(a.getDate());
            appointment.setDoctor(a.getDoctor());
            appointment.setName(a.getName());
            newAppointments.add(appointment);
        });
        return newAppointments;
    }

    private static List<CheckIn> copyCheckins(List<CheckIn> checkIns) {
        List<CheckIn> newCheckIns = new ArrayList<>();
        checkIns.forEach(c -> {
            CheckIn checkIn = new CheckIn();
            checkIn.setFrecuency(c.getFrecuency());
            checkIn.setQuestion(c.getQuestion());
            checkIn.setStartAt(c.getStartAt());
            checkIn.setName(c.getName());
            newCheckIns.add(checkIn);
        });
        return newCheckIns;
    }


    private static List<Medication> copyMedications(List<Medication> medications) {
        List<Medication> newMedications = new ArrayList<>();
        medications.forEach(m -> {
            Medication medication = new Medication();
            medication.setFrecuency(m.getFrecuency());
            medication.setQuantity(m.getQuantity());
            medication.setStartAt(m.getStartAt());
            medication.setName(m.getName());
            newMedications.add(medication);
        });
        return newMedications;
    }
    
    private static List<Recommendation> copyRecommendations(List<Recommendation> medications) {
        List<Recommendation> newRecommendations = new ArrayList<>();
        medications.forEach(r -> {
            Recommendation recommendation = new Recommendation();
            recommendation.setText(r.getText());
            recommendation.setName(r.getName());
            newRecommendations.add(recommendation);
        });
        return newRecommendations;
    }
}
