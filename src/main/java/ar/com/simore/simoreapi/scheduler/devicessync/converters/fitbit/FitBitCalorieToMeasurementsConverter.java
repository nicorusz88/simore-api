package ar.com.simore.simoreapi.scheduler.devicessync.converters.fitbit;

import ar.com.simore.simoreapi.entities.FitBitCalorieMeasurement;
import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.json.fitbit.calories.ActivitiesCalory;
import ar.com.simore.simoreapi.entities.json.fitbit.calories.FitBitCalories;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts received JSON from API call into the standard measurement for SIMORE
 */
public class FitBitCalorieToMeasurementsConverter {
    private static final Logger logger = Logger.getLogger("synchronizer");

    private static final String CONVERTED = "Converted FitBitCalorieToMeasurement: \n %s";

    /**
     * @param source
     * @return
     */
    public static List<Measurement> convert(final FitBitCalories source) {
        List<Measurement> fitbitCalorieMeasurements = new ArrayList<>();

        final List<ActivitiesCalory> activitiesCalories = source.getActivitiesCalories();
        activitiesCalories.forEach(calorie -> {
            FitBitCalorieMeasurement fitBitCalorieMeasurement = new FitBitCalorieMeasurement();
            try {
                fitBitCalorieMeasurement.setDate(DateUtils.simpleDateFormat.parse(calorie.getDateTime()));
            } catch (ParseException e) {
                logger.error("Error converting date during calories conversion for Fitbit. Format expected " + DateUtils.simpleDateFormat.toPattern() + "received " + calorie.getDateTime());
            }
            fitBitCalorieMeasurement.setValue(calorie.getValue());
            logger.info(String.format(CONVERTED, fitBitCalorieMeasurement.toString()));
            fitbitCalorieMeasurements.add(fitBitCalorieMeasurement);
        });
        return fitbitCalorieMeasurements;
    }
}
