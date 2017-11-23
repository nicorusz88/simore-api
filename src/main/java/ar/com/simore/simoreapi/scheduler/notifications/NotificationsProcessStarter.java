package ar.com.simore.simoreapi.scheduler.notifications;

import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.CheckInResultService;
import ar.com.simore.simoreapi.services.MeasurementService;
import ar.com.simore.simoreapi.services.NotificationService;
import ar.com.simore.simoreapi.services.TreatmentService;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This bean acts as a starter for the wearables data synchronization service
 */
@Component
public class NotificationsProcessStarter {

    private static final String STARTING_NOTIFICATIONS_PROCESS = "Starting Notifications process";
    private static final String ENDING_NOTIFICATIONS_PROCESS = "Ending Notifications process";
    private static final String NOTIFICATION_PROCESS_TOOK_S_MINUTES = "Notification process took %s minutes";
    private final Logger logger = Logger.getLogger("notifications");


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private MeasurementService measurementService;


    @Autowired
    private CheckInResultService checkInResultService;

    @Autowired
    private NotificationService notificationService;


    @Scheduled(fixedDelay = 300000) //Every 5 minutes
    public void init(){
        final long startTime = System.currentTimeMillis();
        logger.info(STARTING_NOTIFICATIONS_PROCESS);
        List<Notification> notificationList = new ArrayList<>();
        addNotificationsForMedications(notificationList);
        sendNotificationsForAppointments();
        sendNotificationsForRecommendations();
        sendNotificationsForCheckIns();
        final long elapsedTimeInMinutes = DateUtils.getElapsedTimeInMinutes(startTime);
        logger.info(String.format(NOTIFICATION_PROCESS_TOOK_S_MINUTES, elapsedTimeInMinutes));
        logger.info(ENDING_NOTIFICATIONS_PROCESS);
    }

 private void addNotificationsForMedications(final List<Notification> notificationList) {
     notificationList.addAll(notificationService.findByExpectedSendDateBeforeAndReadDateIsNullAndNotificationType(DateUtils.getCurrentDateWithHourOnly(), NotificationTypeEnum.MEDICATION));
    }
    private void sendNotificationsForCheckIns() {
    }
    private void sendNotificationsForAppointments() {
    }
    private void sendNotificationsForRecommendations() {
    }
}
