package ar.com.simore.simoreapi.scheduler.alerts;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.RolesNamesEnum;
import ar.com.simore.simoreapi.repositories.AlertRepository;
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
    private final Logger logger = Logger.getLogger("alerts");

    private static final String RESTING_HEART_RATE = "Resting Heart Rate";
    private static final String STARTING_ALERT_PROCESS = "#######COMENZANDO PROCESO DE ALERTAS#########";
    private static final String ENDING_ALERT_PROCESS = "#######FINALIZANDO DE ALERTAS#########";
    private static final String ALERT_PROCESS_TOOK_S_MINUTES = "El proceso de alertas tomo %s minutos";
    private static final String CANNOT_START_ALERTS_PROCESS_SINCE_IT_IS_ALREADY_STARTED = "No se puede inciiar el proceeso de alertas ya que ya se encuentra iniciado";
    private static final String PROCESS_S_PACIENTS = "Procesando alertas para %s pacientes";
    private static final String PROCESS_PACIENT_S = "Procesando alertas para el paciente %s";
    private static final String NO_ALERTS_FOR_USER_S = "No hay alertas para el usuario %s";
    private static final String ALERT_S_HAS_ALREADY_BEEN_TRIGGERED_TODAY_FOR_USER_S = "La alerta %s ya fue disparada hoy para el usuario %s";
    private static final String THERE_ARE_NO_S_TO_APPLY_ALERT_S_ON_FOR_USER_S = "No hay %s para aplicar la alerta %s en el usuario %s";
    private static final String THREASHOLD_NOT_SURPASSED_ON_ALERT_S_FOR_USER_S = "No se superó el umbral de la alerta %s para el usuario %s ";
    private static final String THREASHOLD_SURPASSED_ON_ALERT_S_FOR_USER_S = "Se superó el umbral de la alerta  alert %s para el usuario %s ";
    private static final String ALERT_SENT_S = "Alerta disparada: %s";
    private static final String MESSAGE_SENT_S = "Mensaje enviado %s";

    /**
     * Indicates if the process is running
     */
    private static AtomicBoolean isRunning = new AtomicBoolean(Boolean.FALSE);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private FitBitHeartRateMeasurementService fitBitHeartRateMeasurementService;

    @Autowired
    private FitBitCaloriesMeasurementService fitBitCalorieMeasurementService;

    @Autowired
    private FitBitDistanceMeasurementService fitBitDistanceMeasurementService;

    @Autowired
    private FitBitWeightMeasurementService fitBitWeightMeasurementService;


    @Scheduled(fixedDelay = 30000, initialDelay = 60000) //Every 1 minutes, 1 minute delay for first execution
    public void init() {
        try{
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
                            if (alert.getTriggeredDate() == null || alert.getTriggeredDate().compareTo(DateUtils.getCurrentDateFirstHour()) != 0) {
                                switch (alert.getAlertTypeEnum()) {
                                    case FITBIT_HEART_RATE_RESTING_HEART:
                                        final List<FitBitHeartRateMeasurement> fitBitHeartRateMeasurementList = fitBitHeartRateMeasurementService.getByTreatmentAndDateRangeParsed(treatment.getId(), DateUtils.getCurrentDateFirstHour(), DateUtils.getCurrentDateLastHour());
                                        if (!fitBitHeartRateMeasurementList.isEmpty()) {
                                            final List<FitBitHeartRateMeasurement> filteredMeasurements = fitBitHeartRateMeasurementList.stream().filter(m -> RESTING_HEART_RATE.equals(m.getName())).collect(Collectors.toList());
                                            if (!filteredMeasurements.isEmpty()) {
                                                filteredMeasurements.forEach(m -> {
                                                    if (m.getMax() > alert.getThreshold()) {
                                                        logger.info(String.format(THREASHOLD_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                                        sendAlertMessageToProfessional(patientToSync, alert, String.valueOf(m.getMax()));
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
                                                    sendAlertMessageToProfessional(patientToSync, alert, m.getValue());
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
                                                final Float floatValue = Float.parseFloat(m.getValue());
                                                if (floatValue < alert.getThreshold()) {
                                                    logger.info(String.format(THREASHOLD_SURPASSED_ON_ALERT_S_FOR_USER_S, alert.getName(), patientToSync.getUserName()));
                                                    sendAlertMessageToProfessional(patientToSync, alert, m.getValue());
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
                                                    sendAlertMessageToProfessional(patientToSync, alert, String.valueOf(m.getWeight()));
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
                logger.info("\n");
                logger.info("\n");
                isRunning.set(false);
            } else {
                logger.warn(CANNOT_START_ALERTS_PROCESS_SINCE_IT_IS_ALREADY_STARTED);
            }
        }catch(Exception e){
            logger.error("An Error ocurred during alerts processing", e);
        }
    }

    private void sendAlertMessageToProfessional(User patientToSync, Alert alert, String obtainedValue) {
        final Message message = getMessage(patientToSync, alert, obtainedValue);
        messageService.sendMessage(message);
        setAlertTriggered(alert);
        logger.info(String.format(ALERT_SENT_S, alert.toString()));
        logger.info(String.format(MESSAGE_SENT_S, message.toString()));
    }

    private void setAlertTriggered(Alert alert) {
        alert.setTriggeredDate(DateUtils.getCurrentDateFirstHour());
        alertRepository.save(alert);
    }

    private Message getMessage(User patientToSync, Alert alert, String obtainedValue) {
        Message message = new Message();
        message.setTo(patientToSync.getProfessional());
        message.setFrom(patientToSync);
        message.setSendDate(DateUtils.getCurrentDate());
        message.setText(String.format(alert.getAlertTypeEnum().getAlertMessageForProfessional(), patientToSync.getUserName(), alert.getThreshold(), obtainedValue));
        return message;
    }


    /**
     * Gets all the pacients that are synchronizable. We filter those pacients that have a treatment,
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
