package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.TreatmentTemplate;
import ar.com.simore.simoreapi.services.TreatmentTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/treatments-templates")
public class TreatmentTemplateController extends BaseController<TreatmentTemplateService, TreatmentTemplate> {

    @Autowired
    private TreatmentTemplateService treatmentTemplateService;

    @Override
    TreatmentTemplateService getService() {
        return treatmentTemplateService;
    }
}
