package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.TreatmentComponentsEnum;
import ar.com.simore.simoreapi.repositories.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentService extends BaseService<TreatmentRepository, Treatment> {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private RecommendationService recommendationService;

    @Override
    protected TreatmentRepository getRepository() {
        return treatmentRepository;
    }

    /**
     * Method to add a treatment component (vital, checkin, medication, etc) to an existing treatment
     *
     * @param treatmentComponent
     * @param treatmentId
     * @return
     */
    public ResponseEntity<Treatment> addTreatmentComponentToTreatment(BaseTreatmentComponent treatmentComponent, Long treatmentId) {
        final Treatment treatment = treatmentRepository.findOne(treatmentId);
        final User user = userService.getByTreatment(treatment);
        TreatmentComponentsEnum treatmentComponentsEnum = TreatmentComponentsEnum.valueOf(treatmentComponent.getClass().getSimpleName());
        switch (treatmentComponentsEnum) {
            case Vital:
                Vital vital = (Vital) treatmentComponent;
                if (!treatmentComponentExists(treatment.getVitals(), vital)) {
                    treatment.getVitals().add(vital);
                    treatmentRepository.save(treatment);
                }
                break;
            case CheckIn:
                CheckIn checkIn = (CheckIn) treatmentComponent;
                if (!treatmentComponentExists(treatment.getCheckIns(), checkIn)) {
                    treatment.getCheckIns().add(checkIn);
                    checkInService.save(checkIn);
                    treatmentRepository.save(treatment);
                    userService.createCheckInResultNotification(user, treatment, checkIn);
                }
                break;
            case Medication:
                Medication medication = (Medication) treatmentComponent;
                if (!treatmentComponentExists(treatment.getMedications(), medication)) {
                    treatment.getMedications().add(medication);
                    medicationService.save(medication);
                    treatmentRepository.save(treatment);
                    userService.createMedicationNotification(user, treatment, medication);
                }
                break;
            case Appointment:
                Appointment appointment = (Appointment) treatmentComponent;
                if (!treatmentComponentExists(treatment.getAppointments(), appointment)) {
                    treatment.getAppointments().add(appointment);
                    appointmentService.save(appointment);
                    treatmentRepository.save(treatment);
                    userService.createAppointmentNotification(user,  appointment);
                }
                break;
            case Recommendation:
                Recommendation recommendation = (Recommendation) treatmentComponent;
                if (!treatmentComponentExists(treatment.getRecommendations(), recommendation)) {
                    treatment.getRecommendations().add(recommendation);
                    recommendationService.save(recommendation);
                    treatmentRepository.save(treatment);
                    userService.createRecommendationNotification(user,  recommendation);
                }
                break;
            default:
                break;
        }
        return ResponseEntity.ok(treatment);
    }

    /**
     * Indicates if a specific treatment component already exists in the treatment
     *
     * @param treatmentComponents All specific tratment compomnents from the treatment
     * @param treatmentComponent  Specific treatment ccomponent
     * @param <T>
     * @param <S>
     * @return
     */
    private <T, S> boolean treatmentComponentExists(List<T> treatmentComponents, S treatmentComponent) {
        for (T treatmentComponent1 : treatmentComponents) {
            if (treatmentComponent1.equals(treatmentComponent)) {
                return true;
            }
        }
        return false;
    }
}
