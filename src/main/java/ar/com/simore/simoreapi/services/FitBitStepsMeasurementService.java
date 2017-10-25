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
        final List<FitBitStepsMeasurement> measurements = fitBitStepsMeasurementRepository.findByTreatmenAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }
}
