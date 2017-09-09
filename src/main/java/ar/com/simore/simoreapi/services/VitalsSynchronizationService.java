package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.VitalsSynchronization;
import ar.com.simore.simoreapi.repositories.VitalsSynchronizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VitalsSynchronizationService extends BaseService<VitalsSynchronizationRepository, VitalsSynchronization> {

    @Autowired
    private VitalsSynchronizationRepository vitalsSynchronizationRepository;

    @Override
    protected VitalsSynchronizationRepository getRepository() {
        return vitalsSynchronizationRepository;
    }
}