package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.CheckInResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInResultRepository extends CrudRepository<CheckInResult, Long> {

}
