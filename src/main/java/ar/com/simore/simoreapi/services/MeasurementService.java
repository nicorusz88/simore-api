package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Measurement;
import ar.com.simore.simoreapi.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService extends BaseService<MeasurementRepository, Measurement> {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    protected MeasurementRepository getRepository() {
        return measurementRepository;
    }

}
