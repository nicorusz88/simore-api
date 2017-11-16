package ar.com.simore.simoreapi.scheduler.devicessync.converters.fitbit;

import ar.com.simore.simoreapi.entities.FitBitWeightMeasurement;
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

    /**
     * @param source
     * @return
     */
    public static List<Measurement> convert(final FitBitWeight source) {
        List<Measurement> fitbitWeightMeasurements = new ArrayList<>();

        final List<Weight> weights = source.getWeight();
        weights.forEach(weight -> {
            FitBitWeightMeasurement fitBitWeightMeasurement = new FitBitWeightMeasurement();
            fitBitWeightMeasurement.setDate(getDate());
            fitBitWeightMeasurement.setBmi(weight.getBmi());
            fitBitWeightMeasurement.setWeight(weight.getWeight());
            fitBitWeightMeasurement.setFat(weight.getFat());
            logger.info(String.format(CONVERTED, fitBitWeightMeasurement.toString()));
            fitbitWeightMeasurements.add(fitBitWeightMeasurement);
        });
        return fitbitWeightMeasurements;
    }

    /**
     * Ges current date plus time from source
     * Time is in "21:10:59" format
     *
     * @return
     */
    private static Date getDate() {
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
