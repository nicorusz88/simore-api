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
    private final Logger logger = Logger.getLogger("notifications");
    private static final String CANNOT_START_NOTIFICATION_PROCESS_SINCE_IT_IS_ALREADY_STARTED = "No se puede iniciar el proceso de notificaciones ya que se encuentra iniciado";
    private static final String FIREBASE_RESPONSE_WAS_NULL_WEIRD = "La respuesta de Firebase fue nula..raro";
    private static final String USER_WITH_ID_S_DOES_NOT_HAVE_DEVICE_TOKEN_WILL_NOT_SENT_NOTITICATION_WITH_ID_S = "Usuario con ID: %s no tiene el token del dispositivo, no se va a enviar la notificacion con ID: %s";
    private static final String FAILED_TO_SEND_MESSAGE_TO_USER_ID_S_USER_NAME_S = "Error al enviar el mensaje al usuario con ID: %s Nombre de usuario: %s";
    private static final String LOOKING_FOR_S_NOTIFICATIONS_WITH_DATE_PRIOR_TO_S = "Buscando notificaciones del tipo %s con fecha anterior a %s";
    private static final String FOUND_S_S_NOTIFICATIONS_TO_SEND = "Se encontraron %s %s notificaciones para enviar";
    private static final String AN_ERROR_OCURRED_WHILE_PARSING_JSSON_RESPONSE = "Ocurrio un error interpretando la respuesta JSON de Firebase";
    private static final String SUCCESS = "success";
    private static final String FIRE_BASE_RESPONSE = "Respuesta de FireBase: ";

    /**
     * Indicates if the process is running
     */
    private static AtomicBoolean isRunning = new AtomicBoolean(Boolean.FALSE);

    private static final String STARTING_NOTIFICATIONS_PROCESS = "#######COMENZANDO PROCESO DE NOTIFICACIONES#########";
    private static final String ENDING_NOTIFICATIONS_PROCESS = "#######FINALIZANDO PROCESO DE NOTIFICACIONES#########";
    private static final String NOTIFICATION_PROCESS_TOOK_S_MINUTES = "El proceso de notificaciones tomo %s minutos";


    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Scheduled(fixedDelay = 30000, initialDelay = 120000) // //Every 30 seconds, 2 minute delay for first execution
    public synchronized void init() {
        try {
            if (!isRunning.get()) {
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
                logger.info("\n");
                logger.info("\n");
                isRunning.set(false);
            } else {
                logger.warn(CANNOT_START_NOTIFICATION_PROCESS_SINCE_IT_IS_ALREADY_STARTED);
            }
        } catch (Exception e) {
            logger.error("An Error ocurred during notifications processing", e);
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
