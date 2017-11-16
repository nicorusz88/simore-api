package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.AppointmentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentStatusRepository extends CrudRepository<AppointmentStatus, Long> {

    List<AppointmentStatus> findByNotificationDate(Date date);
}
