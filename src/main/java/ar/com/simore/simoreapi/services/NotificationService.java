package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService extends BaseService<NotificationRepository, Notification> {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    protected NotificationRepository getRepository() {
        return notificationRepository;
    }

    public List<Notification> findByExpectedSendDateBeforeAndReadDateIsNullAndNotificationType(final Date currentDateWithHourOnly, final NotificationTypeEnum notificationType) {
        return notificationRepository.findByExpectedSendDateBeforeAndReadDateIsNullAndNotificationType(currentDateWithHourOnly, notificationType);
    }
}
