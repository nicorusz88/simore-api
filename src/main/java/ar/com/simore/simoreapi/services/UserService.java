package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.RolesNamesEnum;
import ar.com.simore.simoreapi.repositories.TreatmentTemplateRepository;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.utils.TreatmentTemplateHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class UserService extends BaseService<UserRepository, User> {

    private static final String ROLES_NOT_PRESENT = "Roles not present";
    private static final String TREATMENT_TEMPLATE_NOT_PRESENT = "Treatment Template not present";
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TreatmentTemplateRepository treatmentTemmplateRepository;

    @Autowired
    private MedicationStatusService medicationStatusService;

    @Autowired
    private CheckInResultService checkInResultService;

    @Override
    protected UserRepository getRepository() {
        return userRepository;
    }

    public List<User> getByRoles(String roles) {
        // Yes yes, I know we should use criteria here
        roles = roles.replaceAll(" ", "");
        String[] rolesSplitted = roles.split(",");
        String role1;
        String role2 = "";
        String role3 = "";
        switch (rolesSplitted.length) {
            case 1:
                role1 = rolesSplitted[0];
                break;
            case 2:
                role1 = rolesSplitted[0];
                role2 = rolesSplitted[1];
                break;
            case 3:
                role1 = rolesSplitted[0];
                role2 = rolesSplitted[1];
                role3 = rolesSplitted[2];
                break;
            default:
                return Collections.emptyList();
        }
        return userRepository.findByRoles_NameOrRoles_NameOrRoles_Name(role1, role2, role3);
    }

    @Override
    public <T> ResponseEntity<T> save(User user) {
        Optional<List<Role>> rolesOptional = Optional.ofNullable(user.getRoles());
        if (rolesOptional.isPresent()) {
            List<Role> roles = rolesOptional.get();
            final Optional<Role> hasRolePacient = roles.stream().filter(r ->
                    r.getName().equals(RolesNamesEnum.PATIENT.name())).findAny();
            if (hasRolePacient.isPresent()) {
                final Treatment treatment;
                treatment = assignTreatmentTemplate(user);
                if (treatment == null) {
                    LOGGER.error(TREATMENT_TEMPLATE_NOT_PRESENT);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((T) TREATMENT_TEMPLATE_NOT_PRESENT);
                }
                assignCurrentDateDateToTreatment(treatment);
                user.setTreatment(treatment);
                super.save(user);
            } else {
                super.save(user);
            }
        } else {
            LOGGER.error(ROLES_NOT_PRESENT);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((T) ROLES_NOT_PRESENT);
        }
        return ResponseEntity.ok((T) user);
    }

    /**
     * Sets the "creeatedAt" Date to the treatment
     *
     * @param treatment
     */
    private void assignCurrentDateDateToTreatment(Treatment treatment) {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        treatment.setCreatedAt(currentDate);
    }

    /**
     * it creates a treatment from a template and assigns it to a user
     *
     * @param user
     * @return
     */
    private Treatment assignTreatmentTemplate(User user) {
        final TreatmentTemplate treatmentTemplate = treatmentTemmplateRepository.findOne(user.getTreatment().getTreatmentTemplate().getId());
        if (treatmentTemplate != null) {
            final Treatment treatment = TreatmentTemplateHandler.copyFromTemplate(treatmentTemplate, user.getTreatment());
            createFirstMedicationStatus(treatment);
            createFirstCheckInResult(treatment);
            return treatment;
        } else {
            LOGGER.error(TREATMENT_TEMPLATE_NOT_PRESENT);
            return null;
        }
    }

    /**
     * Creates the first medicatoon status so that the notification job knows the start date
     *
     * @param treatment
     */
    private void createFirstMedicationStatus(final Treatment treatment) {
        treatment.getMedications().forEach(medication -> {
            MedicationStatus medicationStatus = new MedicationStatus();
            medicationStatus.setMedication(medication);
            Calendar cal = Calendar.getInstance();
            cal.setTime(treatment.getCreatedAt());
            cal.set(Calendar.HOUR_OF_DAY, (int) medication.getStartAt());
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            medicationStatus.setNotificationDate(cal.getTime());
            medicationStatusService.save(medicationStatus);
        });
    }

    /**
     * Creates the first checkin result so that the notification job knows that start date
     *
     * @param treatment
     */
    private void createFirstCheckInResult(final Treatment treatment) {
        treatment.getCheckIns().forEach(checkIn -> {
            CheckInResult checkInResult = new CheckInResult();
            checkInResult.setCheckIn(checkIn);
            Calendar cal = Calendar.getInstance();
            cal.setTime(treatment.getCreatedAt());
            cal.set(Calendar.HOUR_OF_DAY, (int) checkIn.getStartAt());
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            checkInResult.setNotificationDate(cal.getTime());
            checkInResultService.save(checkInResult);
        });
    }

    /**
     * Adds the fitbit token to the user
     *
     * @param userId
     * @param oAuthNew
     * @return
     */
    public ResponseEntity addFitbitToken(long userId, OAuth oAuthNew) {
        final User user = userRepository.findOne(userId);
        for (OAuth oAuth : user.getOauths()) {
            if (oAuth.getWearableType().name().equals(oAuthNew.getWearableType().name())) {
                oAuth.setAccess_token(oAuthNew.getAccess_token());
                oAuth.setUser_id(oAuthNew.getUser_id());
                oAuth.setExpires_in(oAuthNew.getExpires_in());
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }
        }
        user.getOauths().add(oAuthNew);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets all patiends from a professional
     *
     * @param professionalID
     * @return
     */
    public List<User> getPatientsFromProfessional(final Long professionalID) {
        final User professional = userRepository.findOne(professionalID);
        return userRepository.findByProfessional(professional);
    }
}
