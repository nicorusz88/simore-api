package ar.com.simore.simoreapi.scheduler.converters;

import ar.com.simore.simoreapi.entities.FitbitHeartRateMeasurement;
import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.ActivitiesHeart;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.FitBitHeartRate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts received JSON from API call into the standard measurement for SIMORE
 */
public class FitBitHeartRateToMeasurementsConverter {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
            try {
                fitbitHeartRateMeasurement.setDate(simpleDateFormat.parse(activitiesHeart.getDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fitbitHeartRateMeasurement.setCaloriesOut(heartRateZone.getCaloriesOut());
            fitbitHeartRateMeasurement.setMax(heartRateZone.getMax());
            fitbitHeartRateMeasurement.setMin(heartRateZone.getMin());
            fitbitHeartRateMeasurement.setName(heartRateZone.getName());
            fitbitHeartRateMeasurement.setMinutes(heartRateZone.getMinutes());
            fitbitHeartRateMeasurements.add(fitbitHeartRateMeasurement);
        }));
        return fitbitHeartRateMeasurements;
    }
}
