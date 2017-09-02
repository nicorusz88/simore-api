package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Vital;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VitalRepository extends CrudRepository<Vital, Long> {

}
