package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Treatment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends CrudRepository<Treatment, Long> {

}
