package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.FitbitHeartRateMeasurement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FitbitHeartRateMeasurementRepository extends CrudRepository<FitbitHeartRateMeasurement, Long> {

    @Query("select fhrm from FitbitHeartRateMeasurement fhrm where fhrm.date = :dateParsed and fhrm.vitalMeasurement.id in (select id from VitalMeasurement where vitalsSynchronization.id in (select id from VitalsSynchronization where treatment.id = :treatmentId))")
    List<FitbitHeartRateMeasurement> findByTreatmenAndDate(@Param("treatmentId")long treatmentId, @Param("dateParsed") Date dateParsed);
}
