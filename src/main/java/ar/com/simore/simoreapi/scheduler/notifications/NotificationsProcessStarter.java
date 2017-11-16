package ar.com.simore.simoreapi.scheduler.notifications;

import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.CheckInResultService;
import ar.com.simore.simoreapi.services.MeasurementService;
import ar.com.simore.simoreapi.services.TreatmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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


    @Scheduled(fixedDelay = 300000) //Every 5 minutes
    public void init(){
        final long startTime = System.currentTimeMillis();
        logger.info(STARTING_NOTIFICATIONS_PROCESS);
        sendNotificationsForMedications();
        sendNotificationsForAppointments();
        sendNotificationsForRecommendations();
        sendNotificationsForCheckIns();
        final long elapsedTimeInMinutes = getElapsedTimeInMinutes(startTime);
        logger.info(String.format(NOTIFICATION_PROCESS_TOOK_S_MINUTES, elapsedTimeInMinutes));
        logger.info(ENDING_NOTIFICATIONS_PROCESS);
    }

    private void sendNotificationsForMedications() {


    }

    private void sendNotificationsForCheckIns() {

    }

    private void sendNotificationsForAppointments() {
    }



    private void sendNotificationsForRecommendations() {

    }

    private long getElapsedTimeInMinutes(long startTime) {
        final long stopTime = System.currentTimeMillis();
        final long elapsedTime = stopTime - startTime;
        return TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
    }
}
