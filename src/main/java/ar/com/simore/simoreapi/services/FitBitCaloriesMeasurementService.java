package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitCalorieMeasurement;
import ar.com.simore.simoreapi.repositories.FitBitCalorieMeasurementRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class FitBitCaloriesMeasurementService extends BaseService<FitBitCalorieMeasurementRepository, FitBitCalorieMeasurement> {
    private final Logger logger = Logger.getLogger(FitBitCaloriesMeasurementService.class);

    @Autowired
    private FitBitCalorieMeasurementRepository fitBitCalorieMeasurementRepository;

    @Override
    protected FitBitCalorieMeasurementRepository getRepository() {
        return fitBitCalorieMeasurementRepository;
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ResponseEntity<List<FitBitCalorieMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        logger.info(String.format("Converting date %s", date));
        final Date dateParsed = simpleDateFormat.parse(date);
        final List<FitBitCalorieMeasurement> measurements = fitBitCalorieMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }

    public ResponseEntity<List<FitBitCalorieMeasurement>> getByTreatmentAndDateRange(long treatmentId, String startDate, String endDate) throws ParseException {
        logger.info(String.format("Converting start date %s", startDate));
        final Date startDateParsed = simpleDateFormat.parse(startDate);
        logger.info(String.format("Converting end date %s", endDate));
        final Date endDateParsed = simpleDateFormat.parse(endDate);
        final List<FitBitCalorieMeasurement> measurements = fitBitCalorieMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDateParsed, endDateParsed);
        return ResponseEntity.ok(measurements);
    }
}
