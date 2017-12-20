package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.CheckIn;
import ar.com.simore.simoreapi.entities.CheckInResult;
import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.entities.resources.PlainAnswer;
import ar.com.simore.simoreapi.services.CheckInService;
import ar.com.simore.simoreapi.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    /**
     * Gets the checkins by user
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<CheckIn>> getCheckInsByUserId(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(checkInService.getByUserId(userId));
    }

    /**
     * We anwer the question
     *
     * @param checkInId
     * @param plainAnswer
     * @return
     */
    @PostMapping("/answer/{checkInId}")
    public ResponseEntity<CheckInResult> answerQuestion(@PathVariable("checkInId") Long checkInId, @RequestBody PlainAnswer plainAnswer) {
        return ResponseEntity.ok( checkInService.answerQuestion(checkInId, plainAnswer.getAnswer()));
    }

    /**
     * Gets the answered checkins for a patient, for the current day
     *
     * @param userId
     * @return
     */
    @GetMapping("/answered/{userId}")
    public ResponseEntity<List<CheckInResult>> getCheckInsAnsweredByUserForCurrentDay(@PathVariable Long userId) {
        return ResponseEntity.ok(checkInService.getAnsweredByUserForCurrentDay(userId));
    }
}
