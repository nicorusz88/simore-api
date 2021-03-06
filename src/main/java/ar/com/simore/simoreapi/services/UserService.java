package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.entities.enums.RolesNamesEnum;
import ar.com.simore.simoreapi.repositories.TreatmentTemplateRepository;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.scheduler.devicessync.SyncProcessStarter;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import ar.com.simore.simoreapi.services.utils.TreatmentTemplateHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends BaseService<UserRepository, User> {

    private static final String ROLES_NOT_PRESENT = "Roles not present";
    private static final String TREATMENT_TEMPLATE_NOT_PRESENT = "Treatment Template not present";
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Value("${avatar.upload.dir}")
    private String avatarUploadDir;


    @Value("${avatar.default}")
    private String defaultAvatar;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TreatmentTemplateRepository treatmentTemmplateRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SyncProcessStarter syncProcessStarter;

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
            if (hasRolePacient.isPresent() && user.getId() == 0) {
                final Treatment treatment;

                treatment = assignTreatmentTemplate(user);
                if (treatment == null) {
                    LOGGER.error(TREATMENT_TEMPLATE_NOT_PRESENT);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((T) TREATMENT_TEMPLATE_NOT_PRESENT);
                }
                user.setTreatment(treatment);
                super.save(user);
                assignCurrentDateDateToTreatment(treatment);
                createFirstMedicationNotifications(user, treatment);
                createFirstCheckInNotification(user, treatment);
                createAppointmentsNotifications(user, treatment);
                createRecommendationsNotifications(user, treatment);
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
            return TreatmentTemplateHandler.copyFromTemplate(treatmentTemplate, user.getTreatment());
        } else {
            LOGGER.error(TREATMENT_TEMPLATE_NOT_PRESENT);
            return null;
        }
    }

    /**
     * Creates the first medication status so that the notification job knows the start date
     *
     * @param treatment
     */
    private void createFirstMedicationNotifications(final User user, final Treatment treatment) {
        treatment.getMedications().forEach(medication -> createMedicationNotification(user, treatment, medication));
    }

    void createMedicationNotification(final User user, final Treatment treatment, final Medication medication) {
        Notification notification = new Notification();
        notification.setNotificationType(NotificationTypeEnum.MEDICATION);
        notification.setUser(user);
        notification.setReferenceId(medication.getId());
        notification.setTitle(NotificationTypeEnum.MEDICATION.getTitle());
        notification.setBody(String.format(NotificationTypeEnum.MEDICATION.getBody(), medication.getQuantity(), medication.getName()));
        notification.setExpectedSendDate(DateUtils.getDateStartAt(treatment.getCreatedAt(), (int) medication.getStartAt()));
        notificationService.save(notification);
    }


    /**
     * Creates the first checkin result so that the notification job knows that start date
     *
     * @param user
     * @param treatment
     */
    private void createFirstCheckInNotification(final User user, final Treatment treatment) {
        treatment.getCheckIns().forEach(checkIn -> createCheckInResultNotification(user, treatment, checkIn));
    }

    void createCheckInResultNotification(final User user, final Treatment treatment, final CheckIn checkIn) {
        Notification notification = new Notification();
        notification.setNotificationType(NotificationTypeEnum.CHECKIN);
        notification.setUser(user);
        notification.setReferenceId(checkIn.getId());
        notification.setTitle(NotificationTypeEnum.CHECKIN.getTitle());
        notification.setBody(String.format(NotificationTypeEnum.CHECKIN.getBody(), checkIn.getQuestion().getQuestion()));
        notification.setExpectedSendDate(DateUtils.getDateStartAt(treatment.getCreatedAt(), (int) checkIn.getStartAt()));
        notificationService.save(notification);
    }

    /**
     * Creates the appointment notification so that the notification job knows that start date
     *
     * @param user
     * @param treatment
     */
    private void createAppointmentsNotifications(final User user, final Treatment treatment) {
        treatment.getAppointments().forEach(appointment -> createAppointmentNotification(user, appointment));
    }

    public void createAppointmentNotification(final User user, final Appointment appointment) {
        Notification notification = new Notification();
        notification.setNotificationType(NotificationTypeEnum.APPOINTMENT);
        notification.setUser(user);
        notification.setReferenceId(appointment.getId());
        notification.setTitle(NotificationTypeEnum.APPOINTMENT.getTitle());
        notification.setBody(String.format(NotificationTypeEnum.APPOINTMENT.getBody(), appointment.getDoctor(), appointment.getAddress(), DateUtils.formatDateHourAndMinutes(appointment.getDate())));
        notification.setExpectedSendDate(appointment.getDate());
        notificationService.save(notification);
    }


    /**
     * Creates the recommendation notification so that the notification job knows that start date
     *
     * @param user
     * @param treatment
     */
    private void createRecommendationsNotifications(final User user, final Treatment treatment) {
        treatment.getRecommendations().forEach(recommendation -> createRecommendationNotification(user, recommendation));
    }

    public void createRecommendationNotification(final User user, final Recommendation recommendation) {
        Notification notification = new Notification();
        notification.setNotificationType(NotificationTypeEnum.RECOMMENDATION);
        notification.setUser(user);
        notification.setReferenceId(recommendation.getId());
        notification.setTitle(NotificationTypeEnum.RECOMMENDATION.getTitle());
        notification.setBody(String.format(NotificationTypeEnum.RECOMMENDATION.getBody(), recommendation.getText()));
        notification.setExpectedSendDate(DateUtils.getCurrentDatePlusHalfAnHour());
        notificationService.save(notification);
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
                syncProcessStarter.init();
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

    public User getByTreatment(final Treatment treatment) {
        return userRepository.findByTreatment(treatment);
    }

    /**
     * Save the uploaded image into configued dir
     *
     * @param multipartFiles
     * @return
     */
    public User saveAvatar(Long userId, List<MultipartFile> multipartFiles) {
        final User user = userRepository.findOne(userId);
        for (MultipartFile file : multipartFiles) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes;
            try {
                bytes = file.getBytes();
                Path path = Paths.get(avatarUploadDir + file.getOriginalFilename());
                Files.write(path, bytes);
                user.setAvatar(file.getOriginalFilename());
                userRepository.save(user);
                return user;
            } catch (IOException e) {
                LOGGER.error("An error ocurred uploading the avatar", e);
            }
        }
        return null;
    }

    /**
     * Gets the user avatar. If none then default is sent
     *
     * @param userId
     * @return
     */
    public byte[] getAvatar(Long userId) {
        final User user = userRepository.findOne(userId);
        InputStream in;
        try {
            String path;
            if (user != null && user.getAvatar() != null) {
                path = avatarUploadDir + user.getAvatar();
            } else {
                path = avatarUploadDir + defaultAvatar;
            }
            File image = new File(path);
            in = FileUtils.openInputStream(image);
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            LOGGER.error("An error ocurred retrieving the avatar", e);
        }
        return null;
    }
}
