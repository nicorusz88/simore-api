package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.AppointmentStatus;
import ar.com.simore.simoreapi.repositories.AppointmentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentStatusService extends BaseService<AppointmentStatusRepository, AppointmentStatus> {

    @Autowired
    private AppointmentStatusRepository appointmentStatusRepository;

    @Override
    protected AppointmentStatusRepository getRepository() {
        return appointmentStatusRepository;
    }

    public List<AppointmentStatus> findByNotificationDate(final Date currentDateWithHourOnly) {
        return appointmentStatusRepository.findByNotificationDate(currentDateWithHourOnly);
    }

}
