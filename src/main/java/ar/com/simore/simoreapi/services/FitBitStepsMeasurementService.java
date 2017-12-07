package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitStepsMeasurement;
import ar.com.simore.simoreapi.repositories.FitBitStepsMeasurementRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FitBitStepsMeasurementService extends BaseService<FitBitStepsMeasurementRepository, FitBitStepsMeasurement> {
    private final Logger logger = Logger.getLogger(FitBitStepsMeasurementService.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private FitBitStepsMeasurementRepository fitBitStepsMeasurementRepository;

    @Override
    protected FitBitStepsMeasurementRepository getRepository() {
        return fitBitStepsMeasurementRepository;
    }



    public ResponseEntity<List<FitBitStepsMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        logger.info(String.format("Converting date %s", date));
        final Date dateParsed = simpleDateFormat.parse(date);
        final List<FitBitStepsMeasurement> measurements = fitBitStepsMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }

    public ResponseEntity<List<FitBitStepsMeasurement>> getByTreatmentAndDateRange(long treatmentId, String startDate, String endDate) throws ParseException {
        logger.info(String.format("Converting start date %s", startDate));
        final Date startDateParsed = simpleDateFormat.parse(startDate);
        logger.info(String.format("Converting end date %s", endDate));
        final Date endDateParsed = simpleDateFormat.parse(endDate);
        final List<FitBitStepsMeasurement> measurements = fitBitStepsMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDateParsed, endDateParsed);
        return ResponseEntity.ok(measurements);
    }
}
