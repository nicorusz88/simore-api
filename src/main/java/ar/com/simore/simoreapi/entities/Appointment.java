package ar.com.simore.simoreapi.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Appointment")
public class Appointment extends BaseTreatmentComponent {

    /**
     * Frecuency in to ask for the survey in hours
     */
    private String doctor;

    private Date date;

    private String address;

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
