package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitCalorieMeasurement;
import ar.com.simore.simoreapi.entities.FitBitDistanceMeasurement;
import ar.com.simore.simoreapi.repositories.FitBitDistanceMeasurementRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FitBitDistanceMeasurementService extends BaseService<FitBitDistanceMeasurementRepository, FitBitDistanceMeasurement> {
    private final Logger logger = Logger.getLogger(FitBitDistanceMeasurementService.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private FitBitDistanceMeasurementRepository fitBitDistanceMeasurementRepository;

    @Override
    protected FitBitDistanceMeasurementRepository getRepository() {
        return fitBitDistanceMeasurementRepository;
    }



    public ResponseEntity<List<FitBitDistanceMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        logger.info(String.format("Converting date %s", date));
        final Date dateParsed = simpleDateFormat.parse(date);
        final List<FitBitDistanceMeasurement> measurements = fitBitDistanceMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }

    public ResponseEntity<List<FitBitDistanceMeasurement>> getByTreatmentAndDateRange(long treatmentId, String startDate, String endDate) throws ParseException {
        logger.info(String.format("Converting start date %s", startDate));
        final Date startDateParsed = simpleDateFormat.parse(startDate);
        logger.info(String.format("Converting end date %s", endDate));
        final Date endDateParsed = simpleDateFormat.parse(endDate);
        final List<FitBitDistanceMeasurement> measurements = fitBitDistanceMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDateParsed, endDateParsed);
        return ResponseEntity.ok(measurements);
    }

    public List<FitBitDistanceMeasurement> getByTreatmentAndDateRangeParsed(long treatmentId, Date startDate, Date endDate){
        return fitBitDistanceMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDate, endDate);
    }
}
