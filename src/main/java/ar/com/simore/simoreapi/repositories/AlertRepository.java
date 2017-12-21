package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends CrudRepository<Alert, Long> {

}
