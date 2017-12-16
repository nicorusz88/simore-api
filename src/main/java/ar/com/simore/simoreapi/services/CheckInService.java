package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.CheckIn;
import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.repositories.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<CheckIn> getByUserId(final Long userId) {
        List<CheckIn> checkins = new ArrayList<>();
        final List<Notification> checkInNotifications = notificationService.getByUserIdAndType(userId, NotificationTypeEnum.CHECKIN);
        checkInNotifications.forEach(not -> {
            final CheckIn checkIn = checkInRepository.findOne(not.getReferenceId());
            if (checkIn != null) {
                checkins.add(checkIn);
            }
        });
        return checkins;
    }
}
