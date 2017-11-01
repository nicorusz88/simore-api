package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitStepsMeasurement;
import ar.com.simore.simoreapi.repositories.FitBitStepsMeasurementRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class FitBitStepsMeasurementService extends BaseService<FitBitStepsMeasurementRepository, FitBitStepsMeasurement> {
    @Autowired
    private FitBitStepsMeasurementRepository fitBitStepsMeasurementRepository;

    @Override
    protected FitBitStepsMeasurementRepository getRepository() {
        return fitBitStepsMeasurementRepository;
    }



    public ResponseEntity<List<FitBitStepsMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        final Date dateParsed = DateUtils.simpleDateFormat.parse(date);
        final List<FitBitStepsMeasurement> measurements = fitBitStepsMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }

    public ResponseEntity<List<FitBitStepsMeasurement>> getByTreatmentAndDateRange(long treatmentId, String startDate, String endDate) throws ParseException {
        final Date startDateParsed = DateUtils.simpleDateFormat.parse(startDate);
        final Date endDateParsed = DateUtils.simpleDateFormat.parse(endDate);
        final List<FitBitStepsMeasurement> measurements = fitBitStepsMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDateParsed, endDateParsed);
        return ResponseEntity.ok(measurements);
    }
}
