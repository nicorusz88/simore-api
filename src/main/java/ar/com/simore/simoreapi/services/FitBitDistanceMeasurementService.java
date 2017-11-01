package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitDistanceMeasurement;
import ar.com.simore.simoreapi.repositories.FitBitDistanceMeasurementRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class FitBitDistanceMeasurementService extends BaseService<FitBitDistanceMeasurementRepository, FitBitDistanceMeasurement> {
    @Autowired
    private FitBitDistanceMeasurementRepository fitBitDistanceMeasurementRepository;

    @Override
    protected FitBitDistanceMeasurementRepository getRepository() {
        return fitBitDistanceMeasurementRepository;
    }



    public ResponseEntity<List<FitBitDistanceMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        final Date dateParsed = DateUtils.simpleDateFormat.parse(date);
        final List<FitBitDistanceMeasurement> measurements = fitBitDistanceMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }

    public ResponseEntity<List<FitBitDistanceMeasurement>> getByTreatmentAndDateRange(long treatmentId, String startDate, String endDate) throws ParseException {
        final Date startDateParsed = DateUtils.simpleDateFormat.parse(startDate);
        final Date endDateParsed = DateUtils.simpleDateFormat.parse(endDate);
        final List<FitBitDistanceMeasurement> measurements = fitBitDistanceMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDateParsed, endDateParsed);
        return ResponseEntity.ok(measurements);
    }
}
