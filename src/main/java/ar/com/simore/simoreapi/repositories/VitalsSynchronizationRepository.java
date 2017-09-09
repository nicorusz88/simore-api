package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.VitalsSynchronization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VitalsSynchronizationRepository extends CrudRepository<VitalsSynchronization, Long> {


}
