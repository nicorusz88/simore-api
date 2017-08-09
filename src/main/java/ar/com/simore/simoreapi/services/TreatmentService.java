package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.repositories.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreatmentService extends BaseService<TreatmentRepository, Treatment> {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Override
    protected TreatmentRepository getRepository() {
        return treatmentRepository;
    }

}
