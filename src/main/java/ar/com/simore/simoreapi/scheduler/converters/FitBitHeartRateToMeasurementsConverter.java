package ar.com.simore.simoreapi.scheduler.converters;

import ar.com.simore.simoreapi.entities.FitbitHeartRateMeasurement;
import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.ActivitiesHeart;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.FitBitHeartRate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Converts received JSON from API call into the standard measurement for SIMORE
 */
public class FitBitHeartRateToMeasurementsConverter {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final String RESTING_HEART_RATE = "Resting Heart Rate";

    /**
     * Only those heartRateZones which their mins are different to Zero re converted
     *
     * @param source
     * @return
     */
    public static List<Measurement> convert(final FitBitHeartRate source) {
        List<Measurement> fitbitHeartRateMeasurements = new ArrayList<>();

        final List<ActivitiesHeart> activitiesHearts = source.getActivitiesHeart();
        activitiesHearts.forEach(activitiesHeart -> activitiesHeart.getValue().getHeartRateZones().stream().filter(heartRateZone -> heartRateZone.getMinutes() > 0).forEach(heartRateZone -> {
            FitbitHeartRateMeasurement fitbitHeartRateMeasurement = new FitbitHeartRateMeasurement();
            fitbitHeartRateMeasurement.setDate(getCurrentDate());
            fitbitHeartRateMeasurement.setCaloriesOut(heartRateZone.getCaloriesOut());
            fitbitHeartRateMeasurement.setMax(heartRateZone.getMax());
            fitbitHeartRateMeasurement.setMin(heartRateZone.getMin());
            fitbitHeartRateMeasurement.setName(heartRateZone.getName());
            fitbitHeartRateMeasurement.setMinutes(heartRateZone.getMinutes());
            fitbitHeartRateMeasurements.add(fitbitHeartRateMeasurement);
        }));
        activitiesHearts.forEach(activitiesHeart -> {
            Long restingHeartRate = activitiesHeart.getValue().getRestingHeartRate();
            FitbitHeartRateMeasurement fitbitHeartRateMeasurement = new FitbitHeartRateMeasurement();
            fitbitHeartRateMeasurement.setName(RESTING_HEART_RATE);
            fitbitHeartRateMeasurement.setMin(restingHeartRate);
            fitbitHeartRateMeasurement.setMax(restingHeartRate);
            fitbitHeartRateMeasurement.setDate(getCurrentDate());
            fitbitHeartRateMeasurements.add(fitbitHeartRateMeasurement);
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
