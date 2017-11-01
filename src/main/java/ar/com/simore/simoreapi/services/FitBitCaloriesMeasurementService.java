package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitCalorieMeasurement;
import ar.com.simore.simoreapi.repositories.FitBitCalorieMeasurementRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class FitBitCaloriesMeasurementService extends BaseService<FitBitCalorieMeasurementRepository, FitBitCalorieMeasurement> {
    @Autowired
    private FitBitCalorieMeasurementRepository fitBitCalorieMeasurementRepository;

    @Override
    protected FitBitCalorieMeasurementRepository getRepository() {
        return fitBitCalorieMeasurementRepository;
    }



    public ResponseEntity<List<FitBitCalorieMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        final Date dateParsed = DateUtils.simpleDateFormat.parse(date);
        final List<FitBitCalorieMeasurement> measurements = fitBitCalorieMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }

    public ResponseEntity<List<FitBitCalorieMeasurement>> getByTreatmentAndDateRange(long treatmentId, String startDate, String endDate) throws ParseException {
        final Date startDateParsed = DateUtils.simpleDateFormat.parse(startDate);
        final Date endDateParsed = DateUtils.simpleDateFormat.parse(endDate);
        final List<FitBitCalorieMeasurement> measurements = fitBitCalorieMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDateParsed, endDateParsed);
        return ResponseEntity.ok(measurements);
    }
}
