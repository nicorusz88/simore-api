package ar.com.simore.simoreapi.scheduler.converters.fitbit;

import ar.com.simore.simoreapi.entities.FitbitCalorieMeasurement;
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
            FitbitCalorieMeasurement fitbitCalorieMeasurement = new FitbitCalorieMeasurement();
            try {
                fitbitCalorieMeasurement.setDate(DateUtils.simpleDateFormat.parse(calorie.getDateTime()));
            } catch (ParseException e) {
                logger.error("Error converting date during calories conversion for Fitbit. Format expected " + DateUtils.simpleDateFormat.toPattern() + "received " + calorie.getDateTime());
            }
            fitbitCalorieMeasurement.setValue(calorie.getValue());
            logger.info(String.format(CONVERTED, fitbitCalorieMeasurement.toString()));
            fitbitCalorieMeasurements.add(fitbitCalorieMeasurement);
        });
        return fitbitCalorieMeasurements;
    }
}
