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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This bean acts as a starter for the notification service
 */
@Component
public class NotificationsProcessStarter {
    private static final String CANNOT_START_NOTIFICATION_PROCESS_SINCE_IT_IS_ALREADY_STARTED = "Cannot start notification process since it is already started";
    private static final String FIREBASE_RESPONSE_WAS_NULL_WEIRD = "Firebase response was null, weird...";
    private static final String USER_WITH_ID_S_DOES_NOT_HAVE_DEVICE_TOKEN_WILL_NOT_SENT_NOTITICATION_WITH_ID_S = "User with ID: %s does not have device token, will not sent notitication with ID: %s";
    private static final String FAILED_TO_SEND_MESSAGE_TO_USER_ID_S_USER_NAME_S = "Failed to send message to user ID: %s User Name: %S ";
    private static final String LOOKING_FOR_S_NOTIFICATIONS_WITH_DATE_PRIOR_TO_S = "Looking for %s notifications with date prior to %s ";
    private static final String FOUND_S_S_NOTIFICATIONS_TO_SEND = "Found %s %s notifications to send";
    private static final String AN_ERROR_OCURRED_WHILE_PARSING_JSSON_RESPONSE = "An error ocurred while parsing jsson response";
    private static final String SUCCESS = "success";
    private static final String FIRE_BASE_RESPONSE = "FireBase Response: ";
    private final Logger logger = Logger.getLogger("notifications");

    /**
     * Indicates if the process is running
     */
    private static AtomicBoolean isRunning = new AtomicBoolean(Boolean.FALSE);

    private static final String STARTING_NOTIFICATIONS_PROCESS = "Starting Notifications process";
    private static final String ENDING_NOTIFICATIONS_PROCESS = "Ending Notifications process";
    private static final String NOTIFICATION_PROCESS_TOOK_S_MINUTES = "Notification process took %s minutes";


    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Scheduled(fixedDelay = 120000) //Every 2 minutes
    public synchronized void init() {
        if(!isRunning.get()){
            isRunning.set(true);
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
                        logger.warn(FIREBASE_RESPONSE_WAS_NULL_WEIRD);
                    }

                } else {
                    logger.info(String.format(USER_WITH_ID_S_DOES_NOT_HAVE_DEVICE_TOKEN_WILL_NOT_SENT_NOTITICATION_WITH_ID_S, notification.getUser().getId(), notification.getId()));
                }
            }
            final long elapsedTimeInMinutes = DateUtils.getElapsedTimeInMinutes(startTime);
            logger.info(String.format(NOTIFICATION_PROCESS_TOOK_S_MINUTES, elapsedTimeInMinutes));
            logger.info(ENDING_NOTIFICATIONS_PROCESS);
            isRunning.set(false);
        }else{
            logger.warn(CANNOT_START_NOTIFICATION_PROCESS_SINCE_IT_IS_ALREADY_STARTED);
        }
    }

    private void processResponse(final String response, final Notification notification) {
        logger.info(FIRE_BASE_RESPONSE + response);
        try {
            final JSONObject jsonObjectResponse = (JSONObject) new JSONParser().parse(response);
            if (jsonObjectResponse.containsKey(SUCCESS)) {
                final long success = (long) (jsonObjectResponse.get(SUCCESS));
                if (success == 1) {
                    notification.setActualSendDate(DateUtils.getCurrentDate());
                } else {
                    logger.warn(String.format(FAILED_TO_SEND_MESSAGE_TO_USER_ID_S_USER_NAME_S, notification.getUser().getId(), notification.getUser().getUserName()));
                }
                notification.setFireBaseResponse(response);
                notificationService.save(notification);
            }
        } catch (Exception e) {
            logger.error(AN_ERROR_OCURRED_WHILE_PARSING_JSSON_RESPONSE, e);
        }
    }

    private void addNotificationsForType(final List<Notification> notificationList, final Date dateToSearch, final NotificationTypeEnum notificationTypeEnum) {
        logger.info(String.format(LOOKING_FOR_S_NOTIFICATIONS_WITH_DATE_PRIOR_TO_S, notificationTypeEnum.name(), dateToSearch));
        final List<Notification> medicationNotifications = notificationService.getNotificationsByDateAndType(dateToSearch, notificationTypeEnum);
        logger.info(String.format(FOUND_S_S_NOTIFICATIONS_TO_SEND, medicationNotifications.size(), notificationTypeEnum.name()));
        notificationList.addAll(medicationNotifications);
    }
}
