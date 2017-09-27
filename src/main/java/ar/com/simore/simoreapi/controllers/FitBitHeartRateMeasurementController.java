package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.FitbitHeartRateMeasurement;
import ar.com.simore.simoreapi.services.FitbitHeartRateMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/fitbit-heartrate-measurements")
public class FitBitHeartRateMeasurementController extends BaseController<FitbitHeartRateMeasurementService, FitbitHeartRateMeasurement> {

    @Autowired
    private FitbitHeartRateMeasurementService fitbitHeartRateMeasurementService;

    @Override
    FitbitHeartRateMeasurementService getService() {
        return fitbitHeartRateMeasurementService;
    }


    @GetMapping("/treatment/{treatmentId}/date/{date}")
    public ResponseEntity<List<FitbitHeartRateMeasurement>> getByDate(@PathVariable long treatmentId, @PathVariable String date) throws ParseException {
        return fitbitHeartRateMeasurementService.getByTreatmentAndDate(treatmentId, date);
    }
}
