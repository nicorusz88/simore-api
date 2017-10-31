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
        final List<FitBitDistanceMeasurement> measurements = fitBitDistanceMeasurementRepository.findByTreatmenAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }
}