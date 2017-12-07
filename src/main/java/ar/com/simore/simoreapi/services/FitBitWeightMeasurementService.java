package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitWeightMeasurement;
import ar.com.simore.simoreapi.repositories.FitBitWeightMeasurementRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FitBitWeightMeasurementService extends BaseService<FitBitWeightMeasurementRepository, FitBitWeightMeasurement> {
    private final Logger logger = Logger.getLogger(FitBitWeightMeasurementService.class);

    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private FitBitWeightMeasurementRepository fitBitWeightMeasurementRepository;

    @Override
    protected FitBitWeightMeasurementRepository getRepository() {
        return fitBitWeightMeasurementRepository;
    }



    public ResponseEntity<List<FitBitWeightMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        logger.info(String.format("Converting date %s", date));
        final Date dateParsed = simpleDateFormat.parse(date);
        final List<FitBitWeightMeasurement> measurements = fitBitWeightMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }

    public ResponseEntity<List<FitBitWeightMeasurement>> getByTreatmentAndDateRange(long treatmentId, String startDate, String endDate) throws ParseException {
        logger.info(String.format("Converting start date %s", startDate));
        final Date startDateParsed = simpleDateFormat.parse(startDate);
        logger.info(String.format("Converting end date %s", endDate));
        final Date endDateParsed = simpleDateFormat.parse(endDate);
        final List<FitBitWeightMeasurement> measurements = fitBitWeightMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDateParsed, endDateParsed);
        return ResponseEntity.ok(measurements);
    }
}
