package ar.com.simore.simoreapi.scheduler.devicessync.converters.fitbit;

import ar.com.simore.simoreapi.entities.FitBitStepsMeasurement;
import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.json.fitbit.steps.ActivitiesStep;
import ar.com.simore.simoreapi.entities.json.fitbit.steps.FitBitSteps;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts received JSON from API call into the standard measurement for SIMORE|
 */
public class FitBitStepsToMeasurementsConverter {
    private static final Logger logger = Logger.getLogger("synchronizer");

    private static final String CONVERTED = "Converted FitBitStepsToMeasurement: \n %s";

    /**
     * @param source
     * @return
     */
    public static List<Measurement> convert(final FitBitSteps source) {
        List<Measurement> fitbitCalorieMeasurements = new ArrayList<>();

        final List<ActivitiesStep> activitiesSteps = source.getActivitiesSteps();
        activitiesSteps.forEach(steps -> {
            FitBitStepsMeasurement fitBitStepsMeasurement = new FitBitStepsMeasurement();
            try {
                fitBitStepsMeasurement.setDate(DateUtils.simpleDateFormat.parse(steps.getDateTime()));
            } catch (ParseException e) {
                logger.error("Error converting date during steps conversion for Fitbit. Format expected " + DateUtils.simpleDateFormat.toPattern() + "received " + steps.getDateTime());
            }
            fitBitStepsMeasurement.setValue(steps.getValue());
            logger.info(String.format(CONVERTED, fitBitStepsMeasurement.toString()));
            fitbitCalorieMeasurements.add(fitBitStepsMeasurement);
        });
        return fitbitCalorieMeasurements;
    }
}
