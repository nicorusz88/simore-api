package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.TreatmentTemplate;
import ar.com.simore.simoreapi.repositories.TreatmentTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreatmentTemplateService extends BaseService<TreatmentTemplateRepository, TreatmentTemplate> {

    @Autowired
    private TreatmentTemplateRepository treatmentTemplateRepository;

    @Override
    protected TreatmentTemplateRepository getRepository() {
        return treatmentTemplateRepository;
    }

}
