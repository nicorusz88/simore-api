package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.Appointment;
import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.services.AppointmentService;
import ar.com.simore.simoreapi.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    //TODO: Hacer para obtener los appointments para 5 dias.
}
