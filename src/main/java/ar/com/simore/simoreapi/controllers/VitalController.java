package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.entities.Vital;
import ar.com.simore.simoreapi.services.TreatmentService;
import ar.com.simore.simoreapi.services.VitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vitals")
public class VitalController extends BaseController<VitalService, Vital> {

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private VitalService vitalService;

    @Override
    VitalService getService() {
        return vitalService;
    }


    /** Method to add a vital to an existing treatment
     * @param vital  Vital to add to the treatment
     * @param treatmentId ID of the tratment to add the vital
     * @return
     */
    @PostMapping("/add-to-treatment")
    public ResponseEntity<Treatment> addVitalToTreatment(@RequestParam Long treatmentId, @Valid @RequestBody Vital vital){
        return treatmentService.addTreatmentComponentToTreatment(vital, treatmentId);
    }

    @GetMapping("/availables")
    public ResponseEntity<List<Vital>> getTypesAvailableForTreatment(@RequestParam Long treatmentId){
        return vitalService.getTypesAvailableForTreatment(treatmentId);
    }
}
