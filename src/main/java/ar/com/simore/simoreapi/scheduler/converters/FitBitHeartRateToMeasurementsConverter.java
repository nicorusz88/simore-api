package ar.com.simore.simoreapi.scheduler.converters;

import ar.com.simore.simoreapi.entities.FitbitHeartRateMeasurement;
import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.ActivitiesHeart;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.FitBitHeartRate;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.HeartRateZone;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts received JSON from API call into the standard measurement for SIMORE
 */
public class FitBitHeartRateToMeasurementsConverter {
    private static final Logger logger = Logger.getLogger("synchronizer");
    private static final String RESTING_HEART_RATE = "Resting Heart Rate";

    private static final String CONVERTED = "Converted FitBitHeartRateToMeasurement: \n %s";

    /**
     * Only those heartRateZones which their mins are different to Zero re converted
     *
     * @param source
     * @return
     */
    public static List<Measurement> convert(final FitBitHeartRate source) {
        List<Measurement> fitbitHeartRateMeasurements = new ArrayList<>();

        final List<ActivitiesHeart> activitiesHearts = source.getActivitiesHeart();
        activitiesHearts.forEach(activitiesHeart -> activitiesHeart.getValue().getHeartRateZones().stream().filter(heartRateZone -> (heartRateZone.getMinutes() != null && heartRateZone.getMinutes() > 0)).forEach(heartRateZone -> {
            FitbitHeartRateMeasurement fitbitHeartRateMeasurement = new FitbitHeartRateMeasurement();
            fitbitHeartRateMeasurement.setDate(getCurrentDate());
            fitbitHeartRateMeasurement.setCaloriesOut(heartRateZone.getCaloriesOut());
            fitbitHeartRateMeasurement.setMax(heartRateZone.getMax());
            fitbitHeartRateMeasurement.setMin(heartRateZone.getMin());
            fitbitHeartRateMeasurement.setName(heartRateZone.getName());
            fitbitHeartRateMeasurement.setMinutes(heartRateZone.getMinutes());
            logger.info(String.format(CONVERTED,fitbitHeartRateMeasurement.toString()));
            fitbitHeartRateMeasurements.add(fitbitHeartRateMeasurement);
        }));
        activitiesHearts.forEach(activitiesHeart -> {
            Long restingHeartRate = activitiesHeart.getValue().getRestingHeartRate();
            if(restingHeartRate !=null){
                FitbitHeartRateMeasurement fitbitHeartRateMeasurement = new FitbitHeartRateMeasurement();
                fitbitHeartRateMeasurement.setName(RESTING_HEART_RATE);
                fitbitHeartRateMeasurement.setMin(restingHeartRate);
                fitbitHeartRateMeasurement.setMax(restingHeartRate);
                fitbitHeartRateMeasurement.setDate(getCurrentDate());
                logger.info(String.format(CONVERTED,fitbitHeartRateMeasurement.toString()));
                fitbitHeartRateMeasurements.add(fitbitHeartRateMeasurement);
            }
        });
        return fitbitHeartRateMeasurements;
    }

    /**
     * Ges current date only
     *
     * @return
     */
    private static Date getCurrentDate() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
