package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.MedicationStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationStatusRepository extends CrudRepository<MedicationStatus, Long> {

}
