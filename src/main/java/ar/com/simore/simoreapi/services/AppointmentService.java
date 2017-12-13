package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Appointment;
import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.entities.resources.AppointmentsResource;
import ar.com.simore.simoreapi.repositories.AppointmentRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService extends BaseService<AppointmentRepository, Appointment> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    protected AppointmentRepository getRepository() {
        return appointmentRepository;
    }

    /** Gets the appointments for today, tomorrow and the day after
     * tomorrow to show all of them separate
     * @param userId
     * @return
     */
    public AppointmentsResource getByUserId(Long userId) {
        final List<Appointment> todayAppointments = new ArrayList<>();
        final List<Notification> todayAppointmentsNotifications = notificationService.getByUserIdAndBetweenDates(userId, DateUtils.getCurrentDateFirstHour(), DateUtils.getCurrentDateLastHour(), NotificationTypeEnum.APPOINTMENT);
        todayAppointmentsNotifications.forEach( a -> {
            final Appointment appointment = appointmentRepository.findOne(a.getReferenceId());
            todayAppointments.add(appointment);
        });

        final List<Appointment> tomorrowAppointments = new ArrayList<>();
        final List<Notification> tomorrowAppointmentsNotifications = notificationService.getByUserIdAndBetweenDates(userId, DateUtils.getTomorrowDateFirstHour(), DateUtils.getTomorrowDateLastHour(), NotificationTypeEnum.APPOINTMENT);
        tomorrowAppointmentsNotifications.forEach( a -> {
            final Appointment appointment = appointmentRepository.findOne(a.getReferenceId());
            tomorrowAppointments.add(appointment);
        });

        final List<Appointment> followingAppointments = new ArrayList<>();
        final List<Notification> followingAppointmentsNotifications = notificationService.getByUserIdAndAfterTomorrow(userId, DateUtils.getTomorrowDateLastHour(), NotificationTypeEnum.APPOINTMENT);
        followingAppointmentsNotifications.forEach( a -> {
            final Appointment appointment = appointmentRepository.findOne(a.getReferenceId());
            followingAppointments.add(appointment);
        });

        return new AppointmentsResource(todayAppointments, tomorrowAppointments, followingAppointments);
    }
}
