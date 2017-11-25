package ar.com.simore.simoreapi.scheduler.notifications;

import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.services.NotificationService;
import ar.com.simore.simoreapi.services.PushNotificationService;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
    private NotificationService notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Scheduled(fixedDelay = 300000) //Every 5 minutes
    public void init() {
        final long startTime = System.currentTimeMillis();
        logger.info(STARTING_NOTIFICATIONS_PROCESS);
        List<Notification> notificationList = new ArrayList<>();
        addNotificationsForType(notificationList, DateUtils.getCurrentDateWithHourOnly(), NotificationTypeEnum.MEDICATION);
        addNotificationsForType(notificationList, DateUtils.getCurrentDateWithHourOnly(), NotificationTypeEnum.CHECKIN);
        addNotificationsForType(notificationList, DateUtils.getCurrentDateWithHourAndMinutesOnly(), NotificationTypeEnum.APPOINTMENT);
        addNotificationsForType(notificationList, DateUtils.getCurrentDateWithHourOnly(), NotificationTypeEnum.RECOMMENDATION);
        for (Notification notification : notificationList) {
            if (notification.getUser().getDeviceToken() != null) {
                final String response = pushNotificationService.sendNotification(notification);
                if (response != null) {
                    processResponse(response, notification);
                } else {
                    logger.warn("Firebase response was null, weird...");
                }

            } else {
                logger.info(String.format("User with ID: %s does not have device token, will not sent notitication with ID: %s", notification.getUser().getId(), notification.getId()));
            }
        }
        final long elapsedTimeInMinutes = DateUtils.getElapsedTimeInMinutes(startTime);
        logger.info(String.format(NOTIFICATION_PROCESS_TOOK_S_MINUTES, elapsedTimeInMinutes));
        logger.info(ENDING_NOTIFICATIONS_PROCESS);
    }

    private void processResponse(final String response, final Notification notification) {
        logger.info("FireBase Response: " + response);
        try {
            final JSONObject jsonObjectResponse = (JSONObject) new JSONParser().parse(response);
            if (jsonObjectResponse.containsKey("success")) {
                final long success = (long) (jsonObjectResponse.get("success"));
                if (success == 1) {
                    notification.setActualSendDate(DateUtils.getCurrentDate());
                } else {
                    logger.warn(String.format("Failed to send message to user ID: %s User Name: %S ", notification.getUser().getId(), notification.getUser().getUserName()));
                }
                notification.setFireBaseResponse(response);
                notificationService.save(notification);
            }
        } catch (Exception e) {
            logger.error("An error ocurred while parsing jsson response", e);
        }
    }

    private void addNotificationsForType(final List<Notification> notificationList, final Date dateToSearch, final NotificationTypeEnum notificationTypeEnum) {
        logger.info(String.format("Looking for %s notifications with date prior to %s ", notificationTypeEnum.name(), dateToSearch));
        final List<Notification> medicationNotifications = notificationService.findByExpectedSendDateBeforeAndActualSendDateIsNullAndReadDateIsNullAndNotificationType(dateToSearch, notificationTypeEnum);
        logger.info(String.format("Found %s %s notifications to send", medicationNotifications.size(), notificationTypeEnum.name()));
        notificationList.addAll(medicationNotifications);
    }
}
