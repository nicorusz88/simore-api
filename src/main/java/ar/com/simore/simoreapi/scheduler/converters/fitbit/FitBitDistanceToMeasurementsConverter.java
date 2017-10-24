package ar.com.simore.simoreapi.scheduler.converters.fitbit;

import ar.com.simore.simoreapi.entities.FitbitCalorieMeasurement;
import ar.com.simore.simoreapi.entities.FitbitDistanceMeasurement;
import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.json.fitbit.distance.ActivitiesDistance;
import ar.com.simore.simoreapi.entities.json.fitbit.distance.FitBitDistance;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts received JSON from API call into the standard measurement for SIMORE|
 */
public class FitBitDistanceToMeasurementsConverter {
    private static final Logger logger = Logger.getLogger("synchronizer");

    private static final String CONVERTED = "Converted FitBitDistanceToMeasurement: \n %s";

    /**
     * @param source
     * @return
     */
    public static List<Measurement> convert(final FitBitDistance source) {
        List<Measurement> fitbitCalorieMeasurements = new ArrayList<>();

        final List<ActivitiesDistance> activitiesDistance = source.getActivitiesDistance();
        activitiesDistance.forEach(distance -> {
            FitbitDistanceMeasurement fitbitDistanceMeasurement = new FitbitDistanceMeasurement();
            try {
                fitbitDistanceMeasurement.setDate(DateUtils.simpleDateFormat.parse(distance.getDateTime()));
            } catch (ParseException e) {
                logger.error("Error converting date during distance conversion for Fitbit. Format expected " + DateUtils.simpleDateFormat.toPattern() + "received " + distance.getDateTime());
            }
            fitbitDistanceMeasurement.setValue(distance.getValue());
            logger.info(String.format(CONVERTED, fitbitDistanceMeasurement.toString()));
            fitbitCalorieMeasurements.add(fitbitDistanceMeasurement);
        });
        return fitbitCalorieMeasurements;
    }
}
