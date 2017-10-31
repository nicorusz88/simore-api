package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.FitBitDistanceMeasurement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FitBitDistanceMeasurementRepository extends CrudRepository<FitBitDistanceMeasurement, Long> {

    @Query("select fm from FitBitDistanceMeasurement fm where fm.date = :dateParsed and fm.vitalMeasurement.id in (select id from VitalMeasurement where vitalsSynchronization.id in (select id from VitalsSynchronization where treatment.id = :treatmentId))")
    List<FitBitDistanceMeasurement> findByTreatmenAndDate(@Param("treatmentId") long treatmentId, @Param("dateParsed") Date dateParsed);
}