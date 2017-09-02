package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Appointment;
import ar.com.simore.simoreapi.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService extends BaseService<AppointmentRepository, Appointment> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    protected AppointmentRepository getRepository() {
        return appointmentRepository;
    }

}
