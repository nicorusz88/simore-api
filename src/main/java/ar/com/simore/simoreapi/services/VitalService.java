package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Vital;
import ar.com.simore.simoreapi.repositories.VitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VitalService extends BaseService<VitalRepository, Vital> {

    @Autowired
    private VitalRepository vitalRepository;

    @Override
    protected VitalRepository getRepository() {
        return vitalRepository;
    }

}
