package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.entities.Medication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Long> {

}
