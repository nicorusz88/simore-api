package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.Medication;
import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.entities.resources.MedicationResource;
import ar.com.simore.simoreapi.services.MedicationService;
import ar.com.simore.simoreapi.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController extends BaseController<MedicationService, Medication> {

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private MedicationService medicationService;

    @Override
    MedicationService getService() {
        return medicationService;
    }


    /** Method to add a Medication to an existing treatment
     * @param medication  Medication to add to the treatment
     * @param treatmentId ID of the tratment to add the Medication
     * @return
     */
    @PostMapping("/add-to-treatment")
    public ResponseEntity<Treatment> addMedicationToTreatment(@RequestParam Long treatmentId, @Valid @RequestBody Medication medication){
        return treatmentService.addTreatmentComponentToTreatment(medication, treatmentId);
    }


    /** Gets the medications for a specific user to be shown in the dashboard from the professinal and from the patient
     * @param userId
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<MedicationResource>> getMedicationsByUserId(@PathVariable("id") Long userId){
        return ResponseEntity.ok(medicationService.getByUserId(userId));
    }
}
