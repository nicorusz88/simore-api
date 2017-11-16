package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.MedicationStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MedicationStatusRepository extends CrudRepository<MedicationStatus, Long> {

    List<MedicationStatus> findByNotificationDate(Date currentDateWithHourOnly);
}
