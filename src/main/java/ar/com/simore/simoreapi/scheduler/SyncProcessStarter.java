package ar.com.simore.simoreapi.scheduler;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.RolesNamesEnum;
import ar.com.simore.simoreapi.entities.enums.WearableTypeEnum;
import ar.com.simore.simoreapi.entities.json.fitbit.calories.FitBitCalories;
import ar.com.simore.simoreapi.entities.json.fitbit.distance.FitBitDistance;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.FitBitHeartRate;
import ar.com.simore.simoreapi.entities.json.fitbit.steps.FitBitSteps;
import ar.com.simore.simoreapi.entities.json.fitbit.weight.FitBitWeight;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.scheduler.converters.fitbit.*;
import ar.com.simore.simoreapi.services.MeasurementService;
import ar.com.simore.simoreapi.services.TreatmentService;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private final Logger logger = Logger.getLogger("synchronizer");

    private static final String SYNCHING_VITAL_S_FROM_S_DEVICE = "Synching vital %s from %s device";
    private static final String UNKNOWN_EXCEPTION_MESSAGE = "An unknown issue ocurred while synching vitals information for Pacient %s and Vital %s";
    private static final String RETRIEVING_INFO_FOR_PACIENT_S_VITAL_S_FROM_URL_S = "Retrieving info for pacient %s, vital %s from URL %s ";
    private static final String API_CALL_ERROR = "Oopps, something wen't wrong while synchronizing. API call status Code: %s, API call status Message: %s";
    private static final String REQUEST_URL_WAS = "Request URL was:\n";
    private static final String REQUEST_HEADERS_WERE = "Request HEADERS were:\n";
    private static final String RECEIVED = "RECEIVED \n ";
    private static final String STARTING_DEVICES_SYNCHRONIZATION = "#######STARTING DEVICES SYNCHRONIZATION#########";
    private static final String ENDING_DEVICES_SYNCHRONIZATION = "#######ENDING DEVICES SYNCHRONIZATION#########";
    private static final String DEVICES_SYNCHRONIZATION_TOOK_S_MINUTES = "Devices synchronization took %s minutes";
    private static final String SYNCHING_S_PACIENTS = "Synching %s pacients";
    private static final String SYNCHING_PACIENT_S = "Synching pacient %s";

    /**
     * Global instance of the HTTP transport.
     */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    @Value("${fitbit.base.url}")
    private String fitBitBaseURL;
    //TODO: Search base whitings URL
    @Value("${withings.base.url}")
    private String withingsBaseURL;


    private final ObjectMapper jacksonMappper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private MeasurementService measurementService;


    @Scheduled(fixedDelay = 21600000)
    public void init(){
        final long startTime = System.currentTimeMillis();
        logger.info(STARTING_DEVICES_SYNCHRONIZATION);
        final List<User> pacients = getSynchronizablePacients();
        logger.info(String.format(SYNCHING_S_PACIENTS, pacients.size()));
        for (final User patientToSync : pacients) {
            logger.info(String.format(SYNCHING_PACIENT_S, patientToSync.getUserName()));
            final Treatment treatment = patientToSync.getTreatment();
            final List<Vital> vitals = treatment.getVitals();
            for (final Vital vitalToSync : vitals) {
                logger.info(String.format(SYNCHING_VITAL_S_FROM_S_DEVICE, vitalToSync.getType().name(), vitalToSync.getWearableType().name()));
                final Optional<OAuth> firstOauth = getPatientAndWearableTypeCredentials(patientToSync, vitalToSync);
                if (firstOauth.isPresent()) {
                    final OAuth oAuth = firstOauth.get();
                    final GenericUrl url = generateAPIURL(vitalToSync, oAuth);
                    try {
                        logger.info(String.format(RETRIEVING_INFO_FOR_PACIENT_S_VITAL_S_FROM_URL_S, patientToSync.getUserName(), vitalToSync.toString(), url.toString()));
                        final HttpResponse apiResponse = executeGet(HTTP_TRANSPORT, firstOauth.get().getAccess_token(), url);
                        processResponse(treatment, vitalToSync, apiResponse);
                    } catch (Exception e) {
                        logger.error(String.format(UNKNOWN_EXCEPTION_MESSAGE, patientToSync.getUserName(), vitalToSync.toString()), e);
                    }
                }
            }
            treatmentService.save(treatment);
        }
        final long elapsedTimeInMinutes = getElapsedTimeInMinutes(startTime);
        logger.info(String.format(DEVICES_SYNCHRONIZATION_TOOK_S_MINUTES, elapsedTimeInMinutes));
        logger.info(ENDING_DEVICES_SYNCHRONIZATION);
    }

    private long getElapsedTimeInMinutes(long startTime) {
        final long stopTime = System.currentTimeMillis();
        final long elapsedTime = stopTime - startTime;
        return TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
    }

    private void processResponse(final Treatment treatment, final Vital vitalToSync, final HttpResponse apiResponse) throws IOException {
        if (apiResponse.isSuccessStatusCode()) {
            List<Measurement> measurements = new ArrayList<>();
            final String content = parseAsString(apiResponse.getContent());
            logger.info(RECEIVED + content);
            switch (vitalToSync.getType()) {
                case HEART_RATE:
                    if(vitalToSync.getWearableType().equals(WearableTypeEnum.FITBIT)){
                        //ClassLoader classLoader = getClass().getClassLoader();
                        //TODO: We read from file with data temporarily
                        //File file = new File(classLoader.getResource("jsonexamples/heartRateZones.json").getFile());
                        //final FitBitHeartRate fitBitHeartRate = jacksonMappper.readValue(file, FitBitHeartRate.class);
                        final FitBitHeartRate fitBitHeartRate = jacksonMappper.readValue(content, FitBitHeartRate.class);
                        measurements.addAll(FitBitHeartRateToMeasurementsConverter.convert(fitBitHeartRate));
                    }else{
                        //TODO: Do converter for withings
                    }
                    break;
                case BLOOD_PRESSURE:
                    if(vitalToSync.getWearableType().equals(WearableTypeEnum.FITBIT)){
                        //TODO: Do converter for fitbit
                    }else{
                        //TODO: Do converter for withings
                    }
                    break;
                case BURNT_CALORIES:
                    if(vitalToSync.getWearableType().equals(WearableTypeEnum.FITBIT)){
                        final FitBitCalories fitBitCalories = jacksonMappper.readValue(content, FitBitCalories.class);
                        measurements.addAll(FitBitCalorieToMeasurementsConverter.convert(fitBitCalories));
                    }else{
                        //TODO: Do converter for withings
                    }
                    break;
                case STEPS:
                    if(vitalToSync.getWearableType().equals(WearableTypeEnum.FITBIT)){
                        final FitBitSteps fitBitSteps = jacksonMappper.readValue(content, FitBitSteps.class);
                        measurements.addAll(FitBitStepsToMeasurementsConverter.convert(fitBitSteps));
                    }else{
                        //TODO: Do converter for withings
                    }
                    break;
                case WEIGHT:
                    if(vitalToSync.getWearableType().equals(WearableTypeEnum.FITBIT)){
                        final FitBitWeight fitBitWeight = jacksonMappper.readValue(content, FitBitWeight.class);
                        measurements.addAll(FitBitWeightToMeasurementsConverter.convert(fitBitWeight));
                    }else{
                        //TODO: Do converter for withings
                    }

                    break;
                case DISTANCE:
                    if(vitalToSync.getWearableType().equals(WearableTypeEnum.FITBIT)){
                        final FitBitDistance fitBitDistance = jacksonMappper.readValue(content, FitBitDistance.class);
                        measurements.addAll(FitBitDistanceToMeasurementsConverter.convert(fitBitDistance));
                    }else{
                        //TODO: Do converter for withings
                    }
                    break;
                case BLOOD_OXYGEN:
                    if(vitalToSync.getWearableType().equals(WearableTypeEnum.FITBIT)){
                        //TODO: Do converter for fitbit
                    }else{
                        //TODO: Do converter for withings
                    }
                    break;
                case SLEEP_TRACKING:
                    if(vitalToSync.getWearableType().equals(WearableTypeEnum.FITBIT)){
                        //TODO: Do converter for fitbit. Fitbit JSOn Response is INVALID JSON
                    }else{
                        //TODO: Do converter for withings
                    }
                    break;
            }
            if(!measurements.isEmpty()){
                removeExistingMeasurementsFromCurrentDate(treatment, vitalToSync);
            }
            setDataToTreatment(treatment, vitalToSync, measurements);
        } else {
            logger.error(String.format(API_CALL_ERROR, apiResponse.getStatusCode(), apiResponse.getStatusMessage()));
            logger.error(REQUEST_URL_WAS + apiResponse.getRequest().getUrl().toString());
            logger.error(REQUEST_HEADERS_WERE + apiResponse.getRequest().getHeaders().toString());
        }
    }

    /**
     * Iterates the measurements from a specific vital and removes htthe existing from the current date..
     * This is to avoidd having duplicates for the same day
     *
     * @param treatment
     * @param vitalToSync
     */
    private void removeExistingMeasurementsFromCurrentDate(Treatment treatment, Vital vitalToSync) {
        logger.info(String.format("Cleaning existing measurements for today %s ", getCurrentDate()));
        final VitalsSynchronization vitalsSynchronization = getVitalsSynchronization(treatment);
        final VitalMeasurement vitalMeasurement = getVitalMeasurement(vitalToSync, vitalsSynchronization);
        if (vitalMeasurement.getMeasurements() != null && !vitalMeasurement.getMeasurements().isEmpty()) {
            final List<Measurement> measurementsFromCurrentDate = vitalMeasurement.getMeasurements().stream().filter(measurement -> measurement.getDate().compareTo(getCurrentDate()) == 0).collect(Collectors.toList());
            for (Measurement measurementToDelete : measurementsFromCurrentDate) {
                logger.info(String.format("Removing measurement %s ", measurementToDelete.toString()));
                vitalMeasurement.getMeasurements().remove(measurementToDelete);
            }
        }
    }

    /**
     * Sets the synched data to the treatment off the patient
     *
     * @param treatment
     * @param vitalToSync
     * @param measurements
     */
    private void setDataToTreatment(Treatment treatment, Vital vitalToSync, List<Measurement> measurements) {
        final VitalsSynchronization vitalsSynchronization = getVitalsSynchronization(treatment);
        final VitalMeasurement vitalMeasurement = getVitalMeasurement(vitalToSync, vitalsSynchronization);
        measurements.forEach(measurement -> measurement.setVitalMeasurement(vitalMeasurement));
        Optional<List<Measurement>> measurementsOpt = Optional.ofNullable(vitalMeasurement.getMeasurements());
        if (measurementsOpt.isPresent()) {
            vitalMeasurement.getMeasurements().addAll(measurements);
        } else {
            final List<Measurement> measurementList = new ArrayList<>();
            measurementList.addAll(measurements);
            vitalMeasurement.setMeasurements(measurementList);
        }
        setVitalMeasurementToVitalsSynchronization(vitalsSynchronization, vitalMeasurement);
        treatment.setVitalsSynchronization(vitalsSynchronization);
        vitalsSynchronization.setLastSuccessfulSync(getCurrentDateTime());
    }

    /**
     * Sets the vital measurement into the vital synchronization
     *
     * @param vitalsSynchronization
     * @param vitalMeasurement
     */
    private void setVitalMeasurementToVitalsSynchronization(VitalsSynchronization vitalsSynchronization, VitalMeasurement vitalMeasurement) {
        if (vitalMeasurement.getId() == 0) {
            if (vitalsSynchronization.getVitalsMeasurements() != null) {
                vitalsSynchronization.getVitalsMeasurements().add(vitalMeasurement);
            } else {
                List<VitalMeasurement> vitalMeasurements = new ArrayList<>();
                vitalMeasurements.add(vitalMeasurement);
                vitalsSynchronization.setVitalsMeasurements(vitalMeasurements);
            }
        }
    }


    /**
     * Generates the URL API depending on the device brand
     *
     * @param vitalToSync
     * @param oAuth
     * @return
     */
    private GenericUrl generateAPIURL(Vital vitalToSync, OAuth oAuth) {
        GenericUrl url = null;
        switch (vitalToSync.getWearableType()) {
            case FITBIT:
                url = generateFitbitVitalConnectUrl(vitalToSync, oAuth);
                break;
            case WITHINGS:
                //TODO: Create withings URL
                url = new GenericUrl(withingsBaseURL);
                logger.warn("WITHINGS CONNECTION NOT IMPLEMENTED");
                break;
        }
        return url;
    }

    /**
     * Gets the existing vitalMeasurement  from VitalsSynchronization or creates and assigns a new one to the vitalsSynchronization
     *
     * @param vitalToSync
     * @param vitalsSynchronization
     * @return
     */
    private VitalMeasurement getVitalMeasurement(Vital vitalToSync, VitalsSynchronization vitalsSynchronization) {
        final Optional<List<VitalMeasurement>> vitalsMeasurementsOptional = Optional.ofNullable(vitalsSynchronization.getVitalsMeasurements());
        if (vitalsMeasurementsOptional.isPresent()) {
            final Optional<VitalMeasurement> vitalMeasurementOptional = vitalsSynchronization.getVitalsMeasurements().stream().filter(vm -> vm.getVital().getType().name().equals(vitalToSync.getType().name())).findFirst();
            if (vitalMeasurementOptional.isPresent()) {
                return vitalMeasurementOptional.get();
            }
        }
        VitalMeasurement vitalMeasurement = new VitalMeasurement();
        vitalMeasurement.setVital(vitalToSync);
        vitalMeasurement.setVitalsSynchronization(vitalsSynchronization);
        return vitalMeasurement;
    }

    /**
     * Gets the existing vitals synmchronizations from treatment or creates and assigns a new one to the treatment
     *
     * @param treatment
     * @return
     */
    private VitalsSynchronization getVitalsSynchronization(Treatment treatment) {
        VitalsSynchronization vitalsSynchronization;
        final Optional<VitalsSynchronization> vitalsSynchronizationOptional = Optional.ofNullable(treatment.getVitalsSynchronization());
        if (vitalsSynchronizationOptional.isPresent()) {
            vitalsSynchronization = treatment.getVitalsSynchronization();
        } else {
            vitalsSynchronization = new VitalsSynchronization();
            treatment.setVitalsSynchronization(vitalsSynchronization);
            vitalsSynchronization.setTreatment(treatment);
        }
        return vitalsSynchronization;
    }

    /**
     * Gets the pacient and wearable type (fitibit, withings) credentials. In case of not existing the isPresent of the optional is false
     *
     * @param pacientToSync
     * @param vitalToSync
     * @return
     */
    private Optional<OAuth> getPatientAndWearableTypeCredentials(User pacientToSync, Vital vitalToSync) {
        return pacientToSync.getOauths().stream().filter(o -> o.getWearableType().name().equals(vitalToSync.getWearableType().name())).findFirst();
    }

    /**
     * Ges current date and Time
     *
     * @return
     */
    private Date getCurrentDateTime() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Ges current date only
     *
     * @return
     */
    private Date getCurrentDate() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
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

    /**
     * Generates the URL to retrieve the appropriate information for corresponding Vital Type
     *
     * @param vitalToSync Vital to sync
     * @param oAuth       Wearable Type Credentials
     * @return
     */
    private GenericUrl generateFitbitVitalConnectUrl(Vital vitalToSync, OAuth oAuth) {
        final String user_id = oAuth.getUser_id();
        String additionalPart = vitalToSync.getType().getFitbitURL();
        return new GenericUrl(fitBitBaseURL + "/" + user_id + additionalPart);
    }


    private static HttpResponse executeGet(HttpTransport transport, String accessToken, GenericUrl url) throws IOException {
        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken);
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);
        return requestFactory.buildGetRequest(url).execute();
    }

    public String getFitBitBaseURL() {
        return fitBitBaseURL;
    }

    public String getWithingsBaseURL() {
        return withingsBaseURL;
    }

    public String parseAsString(final InputStream content) throws IOException {
        if (content == null) {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(content, out);
        return out.toString(Charsets.ISO_8859_1.name());
    }
}
