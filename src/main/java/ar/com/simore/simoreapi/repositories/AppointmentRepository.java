package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

}
