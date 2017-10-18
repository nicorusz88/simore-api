package ar.com.simore.simoreapi.scheduler.converters.fitbit;

import ar.com.simore.simoreapi.entities.FitbitWeightMeasurement;
import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.json.fitbit.weight.FitBitWeight;
import ar.com.simore.simoreapi.entities.json.fitbit.weight.Weight;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Converts received JSON from API call into the standard measurement for SIMORE
 */
public class FitBitWeightToMeasurementsConverter {
    private static final Logger logger = Logger.getLogger("synchronizer");

    private static final String CONVERTED = "Converted FitBitWeightToMeasurement: \n %s";
    private static final String TIME_SPLIT_CHARACTER = ":";

    /**
     * @param source
     * @return
     */
    public static List<Measurement> convert(final FitBitWeight source) {
        List<Measurement> fitbitWeightMeasurements = new ArrayList<>();

        final List<Weight> weights = source.getWeight();
        weights.forEach(weight -> {
            FitbitWeightMeasurement fitbitWeightMeasurement = new FitbitWeightMeasurement();
            fitbitWeightMeasurement.setDate(getDate(weight.getTime()));
            fitbitWeightMeasurement.setBmi(weight.getBmi());
            fitbitWeightMeasurement.setWeight(weight.getWeight());
            fitbitWeightMeasurement.setFat(weight.getFat());
            logger.info(String.format(CONVERTED, fitbitWeightMeasurement.toString()));
            fitbitWeightMeasurements.add(fitbitWeightMeasurement);
        });
        return fitbitWeightMeasurements;
    }

    /**
     * Ges current date plus time from source
     * Time is in "21:10:59" format
     *
     * @return
     */
    private static Date getDate(final String time) {
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String[] timeParts = time.split(TIME_SPLIT_CHARACTER);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
        cal.set(Calendar.SECOND, Integer.parseInt(timeParts[2]));
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
