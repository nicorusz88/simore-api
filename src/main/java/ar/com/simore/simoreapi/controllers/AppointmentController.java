package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.Appointment;
import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.services.AppointmentService;
import ar.com.simore.simoreapi.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController extends BaseController<AppointmentService, Appointment> {

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private AppointmentService appointmentService;

    @Override
    AppointmentService getService() {
        return appointmentService;
    }


    /** Method to add a appointment to an existing treatment
     * @param appointment  Appointment to add to the treatment
     * @param treatmentId ID of the tratment to add the appointment
     * @return
     */
    @PostMapping("/add-to-treatment")
    public ResponseEntity<Treatment> addAppointmentToTreatment(@RequestParam Long treatmentId, @Valid @RequestBody Appointment appointment){
        return treatmentService.addTreatmentComponentToTreatment(appointment, treatmentId);
    }

    /** Gets the appointments for next five days of a specific user to be shown in the dashboard
     * from the professional and from the patient (mobile)
     * @param userId
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentsByUserId(@PathVariable("id") Long userId){
        return ResponseEntity.ok(appointmentService.getByUserId(userId));
    }

}

