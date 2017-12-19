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
    private static final String OUT_OF_RANGE = "Out of Range";
    private static final String FAT_BURN = "Fat Burn";
    private static final String CARDIO = "Cardio";
    private static final String PEAK = "Peak";
    private static final String FUERA_DE_RANGO = "Fuera de rango";
    private static final String QUEMA_DE_GRASA = "Quema de grasa";
    private static final String PICO = "Pico";

    /**
     * Only those heartRateZones which their mins are different to Zero re converted
     *
     * @param source
     * @return
     */
    public static List<Measurement> convert(final FitBitHeartRate source) {
        List<Measurement> fitbitHeartRateMeasurements = new ArrayList<>();

        final List<ActivitiesHeart> activitiesHearts = source.getActivitiesHeart();
        if(activitiesHearts!=null){
            activitiesHearts.forEach(activitiesHeart -> activitiesHeart.getValue().getHeartRateZones().forEach(heartRateZone -> {
                FitBitHeartRateMeasurement fitBitHeartRateMeasurement = new FitBitHeartRateMeasurement();
                fitBitHeartRateMeasurement.setDate(getCurrentDate());
                fitBitHeartRateMeasurement.setCaloriesOut(heartRateZone.getCaloriesOut());
                fitBitHeartRateMeasurement.setMax(heartRateZone.getMax());
                fitBitHeartRateMeasurement.setMin(heartRateZone.getMin());
                fitBitHeartRateMeasurement.setName(translate(heartRateZone.getName()));
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
        }
        return fitbitHeartRateMeasurements;
    }

    private static String translate(String name) {
        switch (name){
            case OUT_OF_RANGE:
                return FUERA_DE_RANGO;
            case FAT_BURN:
                return QUEMA_DE_GRASA;
            case CARDIO:
                return CARDIO;
            case PEAK:
                return PICO;
        }
        return null;
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
