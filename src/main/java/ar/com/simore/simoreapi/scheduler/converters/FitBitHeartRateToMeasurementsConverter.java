package ar.com.simore.simoreapi.scheduler.converters;

import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.Dataset;
import ar.com.simore.simoreapi.entities.json.fitbit.heartrate.FitBitHeartRate;

import java.util.*;

/**
 * Converts received JSON from API call into the standard measurement for SIMORE
 */
public class FitBitHeartRateToMeasurementsConverter {

    public static List<Measurement> convert(final Date currentDate, final FitBitHeartRate source) {
        List<Measurement> measurements = new ArrayList<>();
        //TODO: Here we need to change in case of isung intraday data. Intraday data is very detailes information for each day, minute by minute or second by second
        final Optional<List<Dataset>> datasetOptional = Optional.ofNullable(source.getActivitiesHeartIntraday().getDataset());
        datasetOptional.ifPresent(datasets -> datasets.forEach(dataset -> {
            Measurement measurement = new Measurement();
            measurement.setDate(getMeasurementDate(currentDate, dataset.getTime()));
            measurement.setValue1(dataset.getValue());
            measurements.add(measurement);
        }));
        return measurements;
    }

    /**
     * Gets the masurement date from the dataset entry. time is in format  hh:mm:ss
     *
     * @param currentDate
     * @param time
     * @return
     */
    private static Date getMeasurementDate(final Date currentDate, final String time) {
        final String[] timeArray = time.split(":");
        final int hour = Integer.parseInt(timeArray[0]);
        final int minute = Integer.parseInt(timeArray[1]);
        final int second = Integer.parseInt(timeArray[2]);
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
