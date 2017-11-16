package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.MedicationStatus;
import ar.com.simore.simoreapi.repositories.MedicationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MedicationStatusService extends BaseService<MedicationStatusRepository, MedicationStatus> {

    @Autowired
    private MedicationStatusRepository medicationStatusRepository;

    @Override
    protected MedicationStatusRepository getRepository() {
        return medicationStatusRepository;
    }

    public List<MedicationStatus> findByNotificationDate(final Date currentDateWithHourOnly) {
        return medicationStatusRepository.findByNotificationDate(currentDateWithHourOnly);
    }
}
