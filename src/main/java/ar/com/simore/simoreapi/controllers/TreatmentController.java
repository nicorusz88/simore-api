package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/treatments")
public class TreatmentController extends BaseController<TreatmentService, Treatment> {

    @Autowired
    private TreatmentService treatmentService;

    @Override
    TreatmentService getService() {
        return treatmentService;
    }

}
