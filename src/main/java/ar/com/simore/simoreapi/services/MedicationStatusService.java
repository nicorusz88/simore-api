package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.MedicationStatus;
import ar.com.simore.simoreapi.repositories.MedicationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicationStatusService extends BaseService<MedicationStatusRepository, MedicationStatus> {

    @Autowired
    private MedicationStatusRepository medicationStatusRepository;

    @Override
    protected MedicationStatusRepository getRepository() {
        return medicationStatusRepository;
    }

}
