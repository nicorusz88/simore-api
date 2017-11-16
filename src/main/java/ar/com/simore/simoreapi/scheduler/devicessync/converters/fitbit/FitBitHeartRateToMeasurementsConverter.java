package ar.com.simore.simoreapi.scheduler.devicessync.converters.fitbit;

import ar.com.simore.simoreapi.entities.FitBitHeartRateMeasurement;
import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.ActivitiesHeart;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.FitBitHeartRate;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        activitiesHearts.forEach(activitiesHeart -> activitiesHeart.getValue().getHeartRateZones().forEach(heartRateZone -> {
            FitBitHeartRateMeasurement fitBitHeartRateMeasurement = new FitBitHeartRateMeasurement();
            fitBitHeartRateMeasurement.setDate(getCurrentDate());
            fitBitHeartRateMeasurement.setCaloriesOut(heartRateZone.getCaloriesOut());
            fitBitHeartRateMeasurement.setMax(heartRateZone.getMax());
            fitBitHeartRateMeasurement.setMin(heartRateZone.getMin());
            fitBitHeartRateMeasurement.setName(heartRateZone.getName());
            fitBitHeartRateMeasurement.setMinutes(heartRateZone.getMinutes());
            logger.info(String.format(CONVERTED, fitBitHeartRateMeasurement.toString()));
            fitbitHeartRateMeasurements.add(fitBitHeartRateMeasurement);
        }));
        activitiesHearts.forEach(activitiesHeart -> {
            Long restingHeartRate = activitiesHeart.getValue().getRestingHeartRate();
            if(restingHeartRate !=null){
                FitBitHeartRateMeasurement fitBitHeartRateMeasurement = new FitBitHeartRateMeasurement();
                fitBitHeartRateMeasurement.setName(RESTING_HEART_RATE);
                fitBitHeartRateMeasurement.setMin(restingHeartRate);
                fitBitHeartRateMeasurement.setMax(restingHeartRate);
                fitBitHeartRateMeasurement.setDate(getCurrentDate());
                logger.info(String.format(CONVERTED, fitBitHeartRateMeasurement.toString()));
                fitbitHeartRateMeasurements.add(fitBitHeartRateMeasurement);
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
