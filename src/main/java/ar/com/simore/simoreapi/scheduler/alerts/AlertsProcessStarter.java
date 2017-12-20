package ar.com.simore.simoreapi.scheduler.alerts;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.RolesNamesEnum;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.*;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * This bean acts as a starter for the alert service
 */
@Component
public class AlertsProcessStarter {
    private static final String RESTING_HEART_RATE = "Resting Heart Rate";
    private static final String STARTING_ALERT_PROCESS = "#######STARTING ALERT PROCESS#########";
    private static final String ENDING_ALERT_PROCESS = "#######ENDING ALERT PROCESS#########";
    private static final String ALERT_PROCESS_TOOK_S_MINUTES = "Alerts process took %s minutes";
    private static final String CANNOT_START_ALERTS_PROCESS_SINCE_IT_IS_ALREADY_STARTED = "Cannot start alerts process since it is already started";
    private static final String PROCESS_S_PACIENTS = "Processing alerts for %s pacients";
    private static final String PROCESS_PACIENT_S = "Processing alerts for pacient %s";
    private static final String NO_ALERTS_FOR_USER_S = "No alerts for user %s";
    private static final String ALERT_S_HAS_ALREADY_BEEN_TRIGGERED_TODAY_FOR_USER_S = "Alert %s has already been triggered today for user %s";
    private static final String THERE_ARE_NO_S_TO_APPLY_ALERT_S_ON_FOR_USER_S = "There are no %s to apply alert %s on for user %s";
    private static final String THREASHOLD_NOT_SURPASSED_ON_ALERT_S_FOR_USER_S = "Threashold not surpassed on alert %s for user %s ";
    private static final String THREASHOLD_SURPASSED_ON_ALERT_S_FOR_USER_S = "Threashold surpassed on alert %s for user %s ";
    private static final String ALERT_SENT_S = "Alert sent: %s";

    private final Logger logger = Logger.getLogger("alerts");

    /**
     * Indicates if the process is running
     */
    private static AtomicBoolean isRunning = new AtomicBoolean(Boolean.FALSE);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FitBitHeartRateMeasurementService fitBitHeartRateMeasurementService;

    @Autowired
    private FitBitCaloriesMeasurementService fitBitCalorieMeasurementService;

    @Autowired
    private FitBitDistanceMeasurementService fitBitDistanceMeasurementService;

    @Autowired
    private FitBitWeightMeasurementService fitBitWeightMeasurementService;


    @Scheduled(fixedDelay = 120000, initialDelay = 60000) //Every 2 minutes, delay to first execution one minute
    public void init() {
        if (!isRunning.get()) {
            isRunning.set(true);
            final long startTime = System.currentTimeMillis();
            logger.info(STARTING_ALERT_PROCESS);
            final List<User> pacients = getSynchronizablePacients();
            logger.info(String.format(PROCESS_S_PACIENTS, pacients.size()));
            for (final User patientToSync : pacients) {
                logger.info(String.format(PROCESS_PACIENT_S, patientToSync.getUserName()));
                final Treatment treatment = patientToSync.getTreatment();
                final List<Alert> alerts = treatment.getAlerts();
                if (!alerts.isEmpty()) {
                    alerts.forEach(alert -> {
                        if (alert.getTriggeredDate() != null || alert.getTriggeredDate().compareTo(DateUtils.getCurrentDateFirstHour()) != 0) {
                            switch (alert.getAlertTypeEnum()) {
                                case FITBIT_HEART_RATE_RESTING_HEART:
                                    final List<FitBitHeartRateMeasurement> fitBitHeartRateMeasurementList = fitBitHeartRateMeasurementService.getByTreatmentAndDateRangeParsed(treatment.getId(), DateUtils.getCurrentDateFirstHour(), DateUtils.getCurrentDateLastHour());
                                    if (!fitBitHeartRateMeasurementList.isEmpty()) {
                                        final List<FitBitHeartRateMeasurement> filteredMeasurements = fitBitHeartRateMeasurementList.stream().filter(m -> RESTING_HEART_RATE.equals(m.getName())).collect(Collectors.toList());
                                        if (!filteredMeasurements.isEmpty()) {
                                            filteredMeasurements.forEach(m -> {
                                                if (m.getMax() > alert.getThreshold()) {
                                                    logger.info(String.format(THREASHOLD_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                                    sendAlertMessageToProfessional(patientToSync, alert);
                                                } else {
                                                    logger.info(String.format(THREASHOLD_NOT_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                                }
                                            });
                                        } else {
                                            logger.info(String.format(THERE_ARE_NO_S_TO_APPLY_ALERT_S_ON_FOR_USER_S, "fitBitHeartRateMeasurements on resting heart rate", alert.getName(), patientToSync.getUserName()));
                                        }
                                    } else {
                                        logger.info(String.format(THERE_ARE_NO_S_TO_APPLY_ALERT_S_ON_FOR_USER_S, "fitBitHeartRateMeasurements", alert.getName(), patientToSync.getUserName()));
                                    }
                                    break;
                                case FITBIT_BURNT_CALORIES:
                                    final List<FitBitCalorieMeasurement> fitBitCalorieMeasurementList = fitBitCalorieMeasurementService.getByTreatmentAndDateRangeParsed(treatment.getId(), DateUtils.getCurrentDateFirstHour(), DateUtils.getCurrentDateLastHour());
                                    if (!fitBitCalorieMeasurementList.isEmpty()) {
                                        fitBitCalorieMeasurementList.forEach(m -> {
                                            if (Long.parseLong(m.getValue()) < alert.getThreshold()) {
                                                logger.info(String.format(THREASHOLD_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                                sendAlertMessageToProfessional(patientToSync, alert);
                                            } else {
                                                logger.info(String.format(THREASHOLD_NOT_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                            }
                                        });
                                    } else {
                                        logger.info(String.format(THERE_ARE_NO_S_TO_APPLY_ALERT_S_ON_FOR_USER_S, "fitBitCaloriesMeasurements", alert.getName(), patientToSync.getUserName()));
                                    }
                                    break;
                                case FITBIT_DISTANCE:
                                    final List<FitBitDistanceMeasurement> fitBitDistanceMeasurementList = fitBitDistanceMeasurementService.getByTreatmentAndDateRangeParsed(treatment.getId(), DateUtils.getCurrentDateFirstHour(), DateUtils.getCurrentDateLastHour());
                                    if (!fitBitDistanceMeasurementList.isEmpty()) {
                                        fitBitDistanceMeasurementList.forEach(m -> {
                                            if (Long.parseLong(m.getValue()) < alert.getThreshold()) {
                                                logger.info(String.format(THREASHOLD_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                                sendAlertMessageToProfessional(patientToSync, alert);
                                            } else {
                                                logger.info(String.format(THREASHOLD_NOT_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                            }
                                        });
                                    } else {
                                        logger.info(String.format(THERE_ARE_NO_S_TO_APPLY_ALERT_S_ON_FOR_USER_S, "fitBitDistanceMeasurements", alert.getName(), patientToSync.getUserName()));
                                    }
                                    break;
                                case FITBIT_WEIGHT:
                                    final List<FitBitWeightMeasurement> fitBitWeightMeasurementList = fitBitWeightMeasurementService.getByTreatmentAndDateRangeParsed(treatment.getId(), DateUtils.getCurrentDateFirstHour(), DateUtils.getCurrentDateLastHour());
                                    if (!fitBitWeightMeasurementList.isEmpty()) {
                                        fitBitWeightMeasurementList.forEach(m -> {
                                            if (m.getWeight() > alert.getThreshold()) {
                                                logger.info(String.format(THREASHOLD_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                                sendAlertMessageToProfessional(patientToSync, alert);
                                            } else {
                                                logger.info(String.format(THREASHOLD_NOT_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                            }
                                        });
                                    } else {
                                        logger.info(String.format(THERE_ARE_NO_S_TO_APPLY_ALERT_S_ON_FOR_USER_S, "fitBitWeightMeasurements", alert.getName(), patientToSync.getUserName()));
                                    }
                                    break;
                                case WITHINGS_BLOOD_OXYGEN:
                                    //TODO: Withings integration not done
                                    break;
                            }

                        } else {
                            logger.info(String.format(ALERT_S_HAS_ALREADY_BEEN_TRIGGERED_TODAY_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                        }

                    });

                } else {
                    logger.info(String.format(NO_ALERTS_FOR_USER_S, patientToSync.getUserName()));
                }
            }
            final long elapsedTimeInMinutes = DateUtils.getElapsedTimeInMinutes(startTime);
            logger.info(String.format(ALERT_PROCESS_TOOK_S_MINUTES, elapsedTimeInMinutes));
            logger.info(ENDING_ALERT_PROCESS);
            isRunning.set(false);
        } else {
            logger.warn(CANNOT_START_ALERTS_PROCESS_SINCE_IT_IS_ALREADY_STARTED);
        }
    }

    private void sendAlertMessageToProfessional(User patientToSync, Alert alert) {
        final Message message = getMessage(patientToSync, alert);
        messageService.sendMessage(message);
        logger.info(String.format(ALERT_SENT_S, alert.toString()));
    }

    private Message getMessage(User patientToSync, Alert alert) {
        Message message = new Message();
        message.setTo(patientToSync.getProfessional());
        User admin = getAdminUser();
        message.setFrom(admin);
        message.setSendDate(DateUtils.getCurrentDate());
        message.setText(alert.getAlertTypeEnum().getAlertMessageForProfessional());
        return message;
    }

    private User getAdminUser() {
        User admin = userRepository.findByUserName("admin");
        if (admin == null) {
            admin = userRepository.findOne(1L);
        }
        return admin;
    }


    /**
     * Gets all the pacients that are synchronizable. //We filter those pacients that have a treatment,
     * that has vitals in their treatment and that they have already authorized the application
     * to communicate with the providers.
     *
     * @return
     */
    private List<User> getSynchronizablePacients() {
        List<User> pacients = userRepository.findByRoles_Name(RolesNamesEnum.PATIENT.name());
        pacients = pacients.stream().filter(p -> (p.getTreatment() != null && !p.getTreatment().getVitals().isEmpty() && !p.getOauths().isEmpty())).collect(Collectors.toList());
        return pacients;
    }
}
