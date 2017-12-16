package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.CheckIn;
import ar.com.simore.simoreapi.repositories.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckInService extends BaseService<CheckInRepository, CheckIn> {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    protected CheckInRepository getRepository() {
        return checkInRepository;
    }

/*    public List<CheckInResource> getByUserId(final Long userId) {
        notificationService.
    }*/
}
