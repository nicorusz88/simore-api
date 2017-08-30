package ar.com.simore.simoreapi.services.utils;

import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.entities.TreatmentTemplate;

import java.util.ArrayList;

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
        treatment.setVitals(new ArrayList<>(treatmentTemplate.getVitals()));
        treatment.setAppointments(new ArrayList<>(treatmentTemplate.getAppointments()));
        treatment.setCheckIns(new ArrayList<>(treatmentTemplate.getCheckIns()));
        treatment.setMedications(new ArrayList<>(treatmentTemplate.getMedications()));
        treatment.setRecommendations(new ArrayList<>(treatmentTemplate.getRecommendations()));
        return treatment;
    }
}
