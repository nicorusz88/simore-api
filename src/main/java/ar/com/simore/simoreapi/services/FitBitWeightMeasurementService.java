package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitWeightMeasurement;
import ar.com.simore.simoreapi.repositories.FitBitWeightMeasurementRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class FitBitWeightMeasurementService extends BaseService<FitBitWeightMeasurementRepository, FitBitWeightMeasurement> {
    @Autowired
    private FitBitWeightMeasurementRepository fitBitWeightMeasurementRepository;

    @Override
    protected FitBitWeightMeasurementRepository getRepository() {
        return fitBitWeightMeasurementRepository;
    }



    public ResponseEntity<List<FitBitWeightMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        final Date dateParsed = DateUtils.simpleDateFormat.parse(date);
        final List<FitBitWeightMeasurement> measurements = fitBitWeightMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }

    public ResponseEntity<List<FitBitWeightMeasurement>> getByTreatmentAndDateRange(long treatmentId, String startDate, String endDate) throws ParseException {
        final Date startDateParsed = DateUtils.simpleDateFormat.parse(startDate);
        final Date endDateParsed = DateUtils.simpleDateFormat.parse(endDate);
        final List<FitBitWeightMeasurement> measurements = fitBitWeightMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDateParsed, endDateParsed);
        return ResponseEntity.ok(measurements);
    }
}
