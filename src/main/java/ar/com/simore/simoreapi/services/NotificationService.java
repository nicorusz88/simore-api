package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.repositories.NotificationRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
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

    public List<Notification> findByExpectedSendDateBeforeAndActualSendDateIsNullAndReadDateIsNullAndNotificationType(final Date currentDateWithHourOnly, final NotificationTypeEnum notificationType) {
        return notificationRepository.findByExpectedSendDateBeforeAndActualSendDateIsNullAndReadDateIsNullAndNotificationType(currentDateWithHourOnly, notificationType);
    }

    /** Sets the notification read date, indicating that the user read it
     * @param id notification ID
     * @return
     */
    public Notification setRead(final Long id) {
        final Notification notification = notificationRepository.findOne(id);
        notification.setReadDate(DateUtils.getCurrentDate());
        notificationRepository.save(notification);
        return notification;
    }

    /** Gets the 10 latest notifications for the user
     * @param userId
     * @return
     */
    public List<Notification>  findByUserId(final Long userId) {
        return notificationRepository.findFirst20ByUser_IdAndActualSendDateIsNotNullOrderByActualSendDateDesc(userId);
    }
}
