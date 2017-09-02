package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.CheckIn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInRepository extends CrudRepository<CheckIn, Long> {

}
