package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.FitBitHeartRateMeasurement;
import ar.com.simore.simoreapi.entities.resources.FitBitHeartRateMeasurementResource;
import ar.com.simore.simoreapi.repositories.FitBitHeartRateMeasurementRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FitBitHeartRateMeasurementService extends BaseService<FitBitHeartRateMeasurementRepository, FitBitHeartRateMeasurement> {
    private final Logger logger = Logger.getLogger(FitBitHeartRateMeasurementService.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private FitBitHeartRateMeasurementRepository fitbitHeartRateMeasurementRepository;

    @Override
    protected FitBitHeartRateMeasurementRepository getRepository() {
        return fitbitHeartRateMeasurementRepository;
    }


    public ResponseEntity<List<FitBitHeartRateMeasurement>> getByTreatmentAndDate(long treatmentId, String date) throws ParseException {
        logger.info(String.format("Converting date %s", date));
        final Date dateParsed = simpleDateFormat.parse(date);
        final List<FitBitHeartRateMeasurement> measurements = fitbitHeartRateMeasurementRepository.findByTreatmentAndDate(treatmentId, dateParsed);
        return ResponseEntity.ok(measurements);
    }

    public ResponseEntity<List<FitBitHeartRateMeasurementResource>> getByTreatmentAndDateRange(long treatmentId, String startDate, String endDate) throws ParseException {
        List<FitBitHeartRateMeasurementResource> fitBitHeartRateMeasurementResourcesList = new ArrayList<>();
        logger.info(String.format("Converting start date %s", startDate));
        final Date startDateParsed = simpleDateFormat.parse(startDate);
        logger.info(String.format("Converting end date %s", endDate));
        final Date endDateParsed = simpleDateFormat.parse(endDate);
        final List<FitBitHeartRateMeasurement> measurements = fitbitHeartRateMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDateParsed, endDateParsed);
        Map<Date, List<FitBitHeartRateMeasurement>> groupByDateMap =
                measurements.stream().collect(Collectors.groupingBy(FitBitHeartRateMeasurement::getDate));
        groupByDateMap.forEach((a, b) -> {
            FitBitHeartRateMeasurementResource fitBitHeartRateMeasurementResource = new FitBitHeartRateMeasurementResource();
            fitBitHeartRateMeasurementResource.setDate(a);
            b.sort(Comparator.comparingLong(FitBitHeartRateMeasurement::getMin));
            fitBitHeartRateMeasurementResource.setFitBitHeartRateMeasurementList(b);
            fitBitHeartRateMeasurementResourcesList.add(fitBitHeartRateMeasurementResource);
        });
        fitBitHeartRateMeasurementResourcesList.sort(Comparator.comparing(FitBitHeartRateMeasurementResource::getDate));
        return ResponseEntity.ok(fitBitHeartRateMeasurementResourcesList);
    }

    public List<FitBitHeartRateMeasurement> getByTreatmentAndDateRangeParsed(long treatmentId, Date startDate, Date endDate) {
        return fitbitHeartRateMeasurementRepository.findByTreatmentAndDateBetween(treatmentId, startDate, endDate);
    }
}
