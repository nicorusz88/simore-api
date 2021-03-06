package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.resources.FitBitHeartRateMeasurementResource;
import ar.com.simore.simoreapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/measurements/fitbit")
public class FitBitMeasurementController {

    @Autowired
    private FitBitHeartRateMeasurementService fitBitHeartRateMeasurementService;

    @Autowired
    private FitBitWeightMeasurementService fitBitWeightMeasurementService;

    @Autowired
    private FitBitDistanceMeasurementService fitBitDistanceMeasurementService;

    @Autowired
    private FitBitCaloriesMeasurementService fitBitCaloriesMeasurementService;

    @Autowired
    private FitBitStepsMeasurementService fitBitStepsMeasurementService;

    @GetMapping("heart-rate/treatment/{treatmentId}/date/{date}")
    public ResponseEntity<List<FitBitHeartRateMeasurement>> getHeartRateByTreatmentAndDate(@PathVariable long treatmentId, @PathVariable String date) throws ParseException {
        return fitBitHeartRateMeasurementService.getByTreatmentAndDate(treatmentId, date);
    }

    @GetMapping("heart-rate/treatment/{treatmentId}/start-date/{startDate}/end-date/{endDate}")
    public ResponseEntity<List<FitBitHeartRateMeasurementResource>> getHeartRateByTreatmentAndDateRange(@PathVariable long treatmentId, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        return fitBitHeartRateMeasurementService.getByTreatmentAndDateRange(treatmentId, startDate, endDate);
    }

    @GetMapping("weight/treatment/{treatmentId}/date/{date}")
    public ResponseEntity<List<FitBitWeightMeasurement>> getWeightByTreatmentAndDate(@PathVariable long treatmentId, @PathVariable String date) throws ParseException {
        return fitBitWeightMeasurementService.getByTreatmentAndDate(treatmentId, date);
    }

    @GetMapping("weight/treatment/{treatmentId}/start-date/{startDate}/end-date/{endDate}")
    public ResponseEntity<List<FitBitWeightMeasurement>> getWeightByTreatmentAndDateRange(@PathVariable long treatmentId, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        return fitBitWeightMeasurementService.getByTreatmentAndDateRange(treatmentId, startDate, endDate);
    }

    @GetMapping("distance/treatment/{treatmentId}/date/{date}")
    public ResponseEntity<List<FitBitDistanceMeasurement>> getDistanceByTreatmentAndDate(@PathVariable long treatmentId, @PathVariable String date) throws ParseException {
        return fitBitDistanceMeasurementService.getByTreatmentAndDate(treatmentId, date);
    }

    @GetMapping("distance/treatment/{treatmentId}/start-date/{startDate}/end-date/{endDate}")
    public ResponseEntity<List<FitBitDistanceMeasurement>> getDistanceByTreatmentAndDateRange(@PathVariable long treatmentId, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        return fitBitDistanceMeasurementService.getByTreatmentAndDateRange(treatmentId, startDate, endDate);
    }

    @GetMapping("burnt-calories/treatment/{treatmentId}/date/{date}")
    public ResponseEntity<List<FitBitCalorieMeasurement>> getCaloriesByTreatmentAndDate(@PathVariable long treatmentId, @PathVariable String date) throws ParseException {
        return fitBitCaloriesMeasurementService.getByTreatmentAndDate(treatmentId, date);
    }

    @GetMapping("burnt-calories/treatment/{treatmentId}/start-date/{startDate}/end-date/{endDate}")
    public ResponseEntity<List<FitBitCalorieMeasurement>> getCaloriesByTreatmentAndDateRange(@PathVariable long treatmentId, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        return fitBitCaloriesMeasurementService.getByTreatmentAndDateRange(treatmentId, startDate, endDate);
    }

    @GetMapping("steps/treatment/{treatmentId}/date/{date}")
    public ResponseEntity<List<FitBitStepsMeasurement>> getStepsByTreatmentAndDate(@PathVariable long treatmentId, @PathVariable String date) throws ParseException {
        return fitBitStepsMeasurementService.getByTreatmentAndDate(treatmentId, date);
    }

    @GetMapping("steps/treatment/{treatmentId}/start-date/{startDate}/end-date/{endDate}")
    public ResponseEntity<List<FitBitStepsMeasurement>> getStepsByTreatmentAndDateRange(@PathVariable long treatmentId, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        return fitBitStepsMeasurementService.getByTreatmentAndDateRange(treatmentId, startDate, endDate);
    }
}
