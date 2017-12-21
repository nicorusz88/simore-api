package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.CheckInResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CheckInResultRepository extends CrudRepository<CheckInResult, Long> {

    List<CheckInResult> findByCheckIn_Id(long id);

    CheckInResult findByCheckIn_IdAndAnsweredDateAfter(long id, Date currentDateFirstHour);
}
