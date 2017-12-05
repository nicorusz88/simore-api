package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Medication;
import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.entities.resources.MedicationResource;
import ar.com.simore.simoreapi.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MedicationService extends BaseService<MedicationRepository, Medication> {

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    protected MedicationRepository getRepository() {
        return medicationRepository;
    }

    /**
     * Find medications for the day by user
     *
     * @param userId
     * @return
     */
    public List<MedicationResource> getByUserId(final Long userId) {
        List<MedicationResource> medicationResources = new ArrayList<>();
        final List<Notification> notifications = notificationService.getLastsByUserIdAndType(userId, NotificationTypeEnum.MEDICATION);
        notifications.forEach(notification -> {
            final Medication medication = medicationRepository.findOne(notification.getReferenceId());
            final Notification previousNotification = notificationService.getByReferenceIdAndActualSendDateIsNotNull(notification.getReferenceId());
            Date previousDate = null;
            if (previousNotification != null) {
                previousDate = previousNotification.getActualSendDate();
            }
            String quantity = String.valueOf(medication.getQuantity()) + " Mg";
            MedicationResource medicationResource = new MedicationResource(medication.getName(), quantity, previousDate, notification.getExpectedSendDate());
            medicationResources.add(medicationResource);
        });
        return medicationResources;
    }
}
