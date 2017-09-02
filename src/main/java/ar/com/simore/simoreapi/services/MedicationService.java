package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Medication;
import ar.com.simore.simoreapi.repositories.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationService extends BaseService<MedicationRepository, Medication> {

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    protected MedicationRepository getRepository() {
        return medicationRepository;
    }

}
