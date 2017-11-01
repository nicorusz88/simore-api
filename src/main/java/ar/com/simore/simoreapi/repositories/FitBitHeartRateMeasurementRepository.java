package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.FitBitHeartRateMeasurement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FitBitHeartRateMeasurementRepository extends CrudRepository<FitBitHeartRateMeasurement, Long> {

    @Query("select fm from FitBitHeartRateMeasurement fm where fm.date = :dateParsed and fm.vitalMeasurement.id in (select id from VitalMeasurement where vitalsSynchronization.id in (select id from VitalsSynchronization where treatment.id = :treatmentId))")
    List<FitBitHeartRateMeasurement> findByTreatmentAndDate(@Param("treatmentId")long treatmentId, @Param("dateParsed") Date dateParsed);

    @Query("select fm from FitBitHeartRateMeasurement fm where fm.date between :startDateParsed and :endDateParsed and fm.vitalMeasurement.id in (select id from VitalMeasurement where vitalsSynchronization.id in (select id from VitalsSynchronization where treatment.id = :treatmentId))")
    List<FitBitHeartRateMeasurement> findByTreatmentAndDateBetween(@Param("treatmentId") long treatmentId, @Param("startDateParsed") Date startDateParsed, @Param("endDateParsed") Date endDateParsed);
}
