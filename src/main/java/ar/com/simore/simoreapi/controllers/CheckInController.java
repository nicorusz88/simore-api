package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.CheckIn;
import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.services.CheckInService;
import ar.com.simore.simoreapi.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/checkins")
public class CheckInController extends BaseController<CheckInService, CheckIn> {

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private CheckInService checkInService;

    @Override
    CheckInService getService() {
        return checkInService;
    }


    /** Method to add a CheckIn to an existing treatment
     * @param checkIn  CheckIn to add to the treatment
     * @param treatmentId ID of the tratment to add the CheckIn
     * @return
     */
    @PostMapping("/add-to-treatment")
    public ResponseEntity<Treatment> addCheckInToTreatment(@RequestParam Long treatmentId, @Valid @RequestBody CheckIn checkIn){
        return treatmentService.addTreatmentComponentToTreatment(checkIn, treatmentId);
    }
}