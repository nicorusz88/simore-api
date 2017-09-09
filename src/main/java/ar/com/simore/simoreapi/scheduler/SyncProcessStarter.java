package ar.com.simore.simoreapi.scheduler;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.RolesNamesEnum;
import ar.com.simore.simoreapi.exceptions.RolesNotPresentException;
import ar.com.simore.simoreapi.exceptions.TreatmentTemplateNotFoundException;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.TreatmentService;
import ar.com.simore.simoreapi.services.VitalsSynchronizationService;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This bean acts as a starter for the wearables data synchronization service
 */
@Component
public class SyncProcessStarter {
    private static final String SYNCHING_VITAL_S_FROM_S_DEVICE = "Synching vital %s from %s device";
    private static final String FITBIT_BASE_DATE = "[base-date]";
    private static final String FITBIT_END_DATE = "[end-date]";
    private static final String UNKNOWN_EXCEPTION_MESSAGE = "An unknown issue ocurred while synching vitals information for Pacient %s and Vital %s";
    private static final String RETRIEVING_INFO_FOR_PACIENT_S_VITAL_S_FROM_URL_S = "Retrieving info for pacient %s, vital %s from URL %s ";
    private final Logger logger = Logger.getLogger("synchronizer");
    private static final String STARTING_DEVICES_SYNCHRONIZATION = "#######STARTING DEVICES SYNCHRONIZATION#########";
    private static final String ENDING_DEVICES_SYNCHRONIZATION = "#######ENDING DEVICES SYNCHRONIZATION#########";
    private static final String DEVICES_SYNCHRONIZATION_TOOK_S_MINUTES = "Devices synchronization took %s minutes";
    private static final String SYNCHING_S_PACIENTS = "Synching %s pacients";
    private static final String SYNCHING_PACIENT_S = "Synching pacient %s";


    /**
     * Global instance of the JSON factory.
     */
    static final JsonFactory JSON_FACTORY = new JacksonFactory();

    private static final String TOKEN_SERVER_URL = "https://api.dailymotion.com/oauth/token";
    private static final String AUTHORIZATION_SERVER_URL = "https://api.dailymotion.com/oauth/authorize";

    /**
     * OAuth 2 scope.
     */
    private static final String SCOPE = "read";

    /**
     * Global instance of the HTTP transport.
     */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private final String fitBitBaseURL = "https://api.fitbit.com/1/user";
    //TODO: Search base whitings URL
    private final String withingsBaseURL = "https://api.fitbit.com/1/user";

    private final SimpleDateFormat fitbitQueryDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TreatmentService treatmentService;

/*    @Autowired
    private VitalsSynchronizationService vitalsSynchronizationService;*/

    @Scheduled(fixedDelay = 21600000)
    public void init() throws TreatmentTemplateNotFoundException, RolesNotPresentException {
        final long startTime = System.currentTimeMillis();
        logger.info(STARTING_DEVICES_SYNCHRONIZATION);
        List<User> pacients = getSynchronizablePacients();
        Date currentDate = getCurrentDate();
        logger.info(String.format(SYNCHING_S_PACIENTS, pacients.size()));
        for (final User pacientToSync : pacients) {
            logger.info(String.format(SYNCHING_PACIENT_S, pacientToSync.getUserName()));
            final Treatment treatment = pacientToSync.getTreatment();
            final List<Vital> vitals = treatment.getVitals();
            for (final Vital vitalToSync : vitals) {
                logger.info(String.format(SYNCHING_VITAL_S_FROM_S_DEVICE, vitalToSync.getType().name(), vitalToSync.getWearableType().name()));
                final Optional<OAuth> firstOauth = getPacientAndWearableTypeCredentials(pacientToSync, vitalToSync);
                if (firstOauth.isPresent()) {
                    final OAuth oAuth = firstOauth.get();
                    GenericUrl url = null;
                    switch (vitalToSync.getWearableType()) {
                        case FITBIT:
                            url = generateFitbitVitalConnectUrl(currentDate, pacientToSync, vitalToSync, oAuth);
                            break;
                        case WITHINGS:
                            //TODO: Create withings URL
                            url = new GenericUrl(withingsBaseURL);
                            logger.warn("WITHINGS CONNECTION NOT IMPLEMENTED");
                            break;
                    }
                    try {
                        logger.info(String.format(RETRIEVING_INFO_FOR_PACIENT_S_VITAL_S_FROM_URL_S, pacientToSync.getUserName(), vitalToSync.toString(), url.toString()));
                        final HttpResponse apiResponse = executeGet(HTTP_TRANSPORT, JSON_FACTORY, firstOauth.get().getAccess_token(), url);
                        logger.info("RECEIVED \n " + apiResponse.parseAsString());

                        final Optional<VitalsSynchronization> vitalsSynchronizationOptional = Optional.ofNullable(treatment.getVitalsSynchronization());
                        VitalsSynchronization vitalsSynchronization;
                        if (vitalsSynchronizationOptional.isPresent()) {
                            vitalsSynchronization = treatment.getVitalsSynchronization();
                        } else {
                            vitalsSynchronization = new VitalsSynchronization();
                            List<VitalMeasurement> vitalMeasurements = new ArrayList<>();
                            vitalsSynchronization.setVitalsMeasurements(vitalMeasurements);
                            treatment.setVitalsSynchronization(vitalsSynchronization);
                        }
                        if (apiResponse.isSuccessStatusCode()) {
                            //vitalsSynchronization.getVitalsMeasurements().add();
                            vitalsSynchronization.setWasSuccess(true);
                        } else {
                            vitalsSynchronization.setWasSuccess(false);
                        }
                        vitalsSynchronization.setLastSync(currentDate);
                        treatmentService.save(treatment);
                    } catch (IOException e) {
                        logger.error(String.format(UNKNOWN_EXCEPTION_MESSAGE, pacientToSync.getUserName(), vitalToSync.toString()), e);
                    }
                }
            }
        }

        final long stopTime = System.currentTimeMillis();
        final long elapsedTime = stopTime - startTime;
        final long elapsedTimeInMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
        logger.info(String.format(DEVICES_SYNCHRONIZATION_TOOK_S_MINUTES, elapsedTimeInMinutes));
        logger.info(ENDING_DEVICES_SYNCHRONIZATION);
    }

    /**
     * Gets the pacient and wearable type (fitibit, withings) credentials. In case of not existing the isPresent of the optional is false
     *
     * @param pacientToSync
     * @param vitalToSync
     * @return
     */
    private Optional<OAuth> getPacientAndWearableTypeCredentials(User pacientToSync, Vital vitalToSync) {
        return pacientToSync.getOauths().stream().filter(o -> o.getWearableType().name().equals(vitalToSync.getWearableType().name())).findFirst();
    }

    private Date getCurrentDate() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Gets all the pacients that are synchronizable
     *
     * @return
     */
    private List<User> getSynchronizablePacients() {
        List<User> pacients = userRepository.findByRoles_Name(RolesNamesEnum.PACIENT.name());
        //We filter those pacients that have a treatment, that has vitals in their treatment and that they have already authorized the application to communicate with the providers.
        pacients = pacients.stream().filter(p -> (p.getTreatment() != null && !p.getTreatment().getVitals().isEmpty() && !p.getOauths().isEmpty())).collect(Collectors.toList());
        return pacients;
    }

    /**
     * Generates the URL to retrieve the appropriate information for corresponding Vital Type
     *
     * @param currentDate   Current Date
     * @param pacientToSync Pacient to Sync
     * @param vitalToSync   Vital to sync
     * @param oAuth         Wearable Type Credentials
     * @return
     */
    private GenericUrl generateFitbitVitalConnectUrl(Date currentDate, User pacientToSync, Vital vitalToSync, OAuth oAuth) {
        final String user_id = oAuth.getUser_id();
        String additionalPart = vitalToSync.getType().getFitbitURL();
        additionalPart = asssignSynchronizationStartDate(pacientToSync, additionalPart);
        additionalPart = assignSynchronizationEndDate(currentDate, additionalPart);
        GenericUrl url = new GenericUrl(fitBitBaseURL + "/" + user_id + additionalPart);
        return url;
    }

    /**
     * Sets the starting date for retrieving information. Example if date is 02/07/2018, information will be retrieved from that date.
     *
     * @param pacientToSync
     * @param additionalPart
     * @return
     */
    private String asssignSynchronizationStartDate(User pacientToSync, String additionalPart) {
        final Optional<VitalsSynchronization> vitalsSynchronizationOptional = Optional.ofNullable(pacientToSync.getTreatment().getVitalsSynchronization());
        //If vitalsSynchronization does not exists or last sync is null (it has never been synchronized yet) we use the start date as the treatment creation date.
        if (vitalsSynchronizationOptional.isPresent()) {
            Date lastSync = pacientToSync.getTreatment().getVitalsSynchronization().getLastSync();
            //If last sync is null (it has never been synchronized yet) we use the start date as the treatment creation date.
            if (lastSync != null) {
                additionalPart = additionalPart.replace(FITBIT_BASE_DATE, fitbitQueryDateFormat.format(lastSync));
            } else {
                additionalPart = additionalPart.replace(FITBIT_BASE_DATE, fitbitQueryDateFormat.format(pacientToSync.getTreatment().getCreatedAt()));
            }
        } else {
            additionalPart = additionalPart.replace(FITBIT_BASE_DATE, fitbitQueryDateFormat.format(pacientToSync.getTreatment().getCreatedAt()));
        }
        return additionalPart;
    }

    /**
     * Sets the ending date for retrieving information. Example if date is 02/05/2018, information will be retrieved until that date.
     *
     * @param currentDate
     * @param additionalPart
     * @return
     */
    private String assignSynchronizationEndDate(Date currentDate, String additionalPart) {
        additionalPart = additionalPart.replace(FITBIT_END_DATE, fitbitQueryDateFormat.format(currentDate));
        return additionalPart;
    }


    private static HttpResponse executeGet(HttpTransport transport, JsonFactory jsonFactory, String accessToken, GenericUrl url) throws IOException {
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        return requestFactory.buildGetRequest(url).execute();
    }
}
