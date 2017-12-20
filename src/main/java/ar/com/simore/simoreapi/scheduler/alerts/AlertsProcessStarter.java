package ar.com.simore.simoreapi.scheduler.alerts;

import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.services.NotificationService;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This bean acts as a starter for the alert service
 */
@Component
public class AlertsProcessStarter {
    private static final String STARTING_ALERT_PROCESS = "Starting Alerts process";
    private static final String ENDING_ALERT_PROCESS = "Ending Alerts process";
    private static final String ALERT_PROCESS_TOOK_S_MINUTES = "Alerts process took %s minutes";
    private static final String CANNOT_START_ALERTS_PROCESS_SINCE_IT_IS_ALREADY_STARTED = "Cannot start alerts process since it is already started";
    private final Logger logger = Logger.getLogger("alerts");

    /**
     * Indicates if the process is running
     */
    private static AtomicBoolean isRunning = new AtomicBoolean(Boolean.FALSE);

    @Autowired
    private NotificationService notificationService;


    @Scheduled(fixedDelay = 120000) //Every 2 minutes
    public void init() {
        if(!isRunning.get()){
            isRunning.set(true);
            final long startTime = System.currentTimeMillis();
            logger.info(STARTING_ALERT_PROCESS);

            final long elapsedTimeInMinutes = DateUtils.getElapsedTimeInMinutes(startTime);
            logger.info(String.format(ALERT_PROCESS_TOOK_S_MINUTES, elapsedTimeInMinutes));
            logger.info(ENDING_ALERT_PROCESS);
            isRunning.set(false);
        }else{
            logger.warn(CANNOT_START_ALERTS_PROCESS_SINCE_IT_IS_ALREADY_STARTED);
        }
    }
}
