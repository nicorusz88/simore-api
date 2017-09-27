package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitbitHeartRateMeasurement;
import ar.com.simore.simoreapi.repositories.FitbitHeartRateMeasurementRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class FitbitHeartRateMeasurementService extends BaseService<FitbitHeartRateMeasurementRepository, FitbitHeartRateMeasurement> {
    @Autowired
    private FitbitHeartRateMeasurementRepository fitbitHeartRateMeasurementRepository;

    @Override
    protected FitbitHeartRateMeasurementRepository getRepository() {
        return fitbitHeartRateMeasurementRepository;
    }



    public ResponseEntity<List<FitbitHeartRateMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        final Date dateParsed = DateUtils.simpleDateFormat.parse(date);
        final List<FitbitHeartRateMeasurement> measurements = fitbitHeartRateMeasurementRepository.findByTreatmenAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }
}
