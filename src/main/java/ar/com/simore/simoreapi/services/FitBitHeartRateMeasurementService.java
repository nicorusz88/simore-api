package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitHeartRateMeasurement;
import ar.com.simore.simoreapi.repositories.FitBitHeartRateMeasurementRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class FitBitHeartRateMeasurementService extends BaseService<FitBitHeartRateMeasurementRepository, FitBitHeartRateMeasurement> {
    @Autowired
    private FitBitHeartRateMeasurementRepository fitbitHeartRateMeasurementRepository;

    @Override
    protected FitBitHeartRateMeasurementRepository getRepository() {
        return fitbitHeartRateMeasurementRepository;
    }



    public ResponseEntity<List<FitBitHeartRateMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        final Date dateParsed = DateUtils.simpleDateFormat.parse(date);
        final List<FitBitHeartRateMeasurement> measurements = fitbitHeartRateMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }
}
