package ar.com.simore.simoreapi.scheduler.notifications;

import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.*;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private PushNotificationService pushNotificationService;


    @Scheduled(fixedDelay = 300000) //Every 5 minutes
    public void init(){
        final long startTime = System.currentTimeMillis();
        logger.info(STARTING_NOTIFICATIONS_PROCESS);
        List<Notification> notificationList = new ArrayList<>();
        addNotificationsForMedications(notificationList);
        sendNotificationsForAppointments(notificationList);
        sendNotificationsForRecommendations(notificationList);
        sendNotificationsForCheckIns(notificationList);
        for (Notification notification : notificationList) {
            pushNotificationService.sendNotification(notification);
        }

        final long elapsedTimeInMinutes = DateUtils.getElapsedTimeInMinutes(startTime);
        logger.info(String.format(NOTIFICATION_PROCESS_TOOK_S_MINUTES, elapsedTimeInMinutes));
        logger.info(ENDING_NOTIFICATIONS_PROCESS);
    }

 private void addNotificationsForMedications(final List<Notification> notificationList) {
     final Date currentDateWithHourOnly = DateUtils.getCurrentDateWithHourOnly();
     logger.info(String.format("Looking for Medications notifications with date prior to %s ", currentDateWithHourOnly));
     final List<Notification> medicationNotifications = notificationService.findByExpectedSendDateBeforeAndReadDateIsNullAndNotificationType(currentDateWithHourOnly, NotificationTypeEnum.MEDICATION);
     logger.info(String.format("Found %s Medications notifications to send", medicationNotifications.size()));
     notificationList.addAll(medicationNotifications);
    }
    private void sendNotificationsForCheckIns(final List<Notification> notificationList) {
        final Date currentDateWithHourOnly = DateUtils.getCurrentDateWithHourOnly();
        logger.info(String.format("Looking for Checkins notifications with date prior to %s ", currentDateWithHourOnly));
        final List<Notification> medicationNotifications = notificationService.findByExpectedSendDateBeforeAndReadDateIsNullAndNotificationType(currentDateWithHourOnly, NotificationTypeEnum.CHECKIN);
        logger.info(String.format("Found %s Checkins notifications to send", medicationNotifications.size()));
        notificationList.addAll(medicationNotifications);
    }
    private void sendNotificationsForAppointments(final List<Notification> notificationList) {
        final Date currentDateWithHourOnly = DateUtils.getCurrentDateWithHourAndMinutesOnly();
        logger.info(String.format("Looking for Appointments notifications with date prior to %s ", currentDateWithHourOnly));
        final List<Notification> medicationNotifications = notificationService.findByExpectedSendDateBeforeAndReadDateIsNullAndNotificationType(currentDateWithHourOnly, NotificationTypeEnum.APPOINTMENT);
        logger.info(String.format("Found %s Appointments notifications to send", medicationNotifications.size()));
        notificationList.addAll(medicationNotifications);
    }
    private void sendNotificationsForRecommendations(final List<Notification> notificationList) {
        final Date currentDateWithHourOnly = DateUtils.getCurrentDateWithHourOnly();
        logger.info(String.format("Looking for Recommendations notifications with date prior to %s ", currentDateWithHourOnly));
        final List<Notification> medicationNotifications = notificationService.findByExpectedSendDateBeforeAndReadDateIsNullAndNotificationType(currentDateWithHourOnly, NotificationTypeEnum.RECOMMENDATION);
        logger.info(String.format("Found %s Recommendations notifications to send", medicationNotifications.size()));
        notificationList.addAll(medicationNotifications);
    }
}
