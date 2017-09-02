package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Medication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long> {

}
