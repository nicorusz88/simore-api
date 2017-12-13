package ar.com.simore.simoreapi.entities.resources;

import ar.com.simore.simoreapi.entities.Appointment;

import java.util.List;

/**
 * Object sent as response for showing appointments
 */
public class AppointmentsResource {

    /**
     * Appointments for today
     */
    private List<Appointment> todayAppointments;

    /**
     * Appointments for today
     */
    private List<Appointment> tomorrowAppointments;

    /**
     * Subsequent for today
     */
    private List<Appointment> followingAppointments;

    public AppointmentsResource(List<Appointment> todayAppointments, List<Appointment> tomorrowAppointments, List<Appointment> followingAppointments) {
        this.todayAppointments = todayAppointments;
        this.tomorrowAppointments = tomorrowAppointments;
        this.followingAppointments = followingAppointments;
    }


    public List<Appointment> getTodayAppointments() {
        return todayAppointments;
    }

    public void setTodayAppointments(List<Appointment> todayAppointments) {
        this.todayAppointments = todayAppointments;
    }

    public List<Appointment> getTomorrowAppointments() {
        return tomorrowAppointments;
    }

    public void setTomorrowAppointments(List<Appointment> tomorrowAppointments) {
        this.tomorrowAppointments = tomorrowAppointments;
    }

    public List<Appointment> getFollowingAppointments() {
        return followingAppointments;
    }

    public void setFollowingAppointments(List<Appointment> followingAppointments) {
        this.followingAppointments = followingAppointments;
    }
}
