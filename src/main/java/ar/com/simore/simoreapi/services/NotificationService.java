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

    public List<Notification> getNotificationsByDateAndType(final Date currentDateWithHourOnly, final NotificationTypeEnum notificationType) {
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
    public List<Notification> getByUserId(final Long userId) {
        return notificationRepository.findFirst20ByUser_IdAndActualSendDateIsNotNullOrderByActualSendDateDesc(userId);
    }

    /** Gets the latest non sent notifications
     * @param userId
     * @param notificationType
     * @return
     */
    public List<Notification> getLastsByUserIdAndType(final Long userId, final NotificationTypeEnum notificationType) {
        return notificationRepository.findByUser_IdAndActualSendDateIsNullAndNotificationTypeOrderByExpectedSendDate(userId, notificationType);
    }

    public Notification getByReferenceIdAndActualSendDateIsNotNull(long referenceId) {
        return notificationRepository.findFirstByReferenceIdAndActualSendDateIsNotNullOrderByActualSendDateDesc(referenceId);
    }

    /** Gets notifications of a certain type, for a specific user and between certain dates
     * @param userId
     * @param lowerDate
     * @param upperDate
     * @param notificationType
     * @return
     */
    public List<Notification> getByUserIdAndBetweenDates(Long userId, Date lowerDate, Date upperDate, NotificationTypeEnum notificationType) {
        return notificationRepository.findByUser_IdAndExpectedSendDateBetweenAndNotificationType(userId, lowerDate, upperDate, notificationType);
    }

    /**Gets notifications of a certain type, for a specific user and after certain date
     * @param userId
     * @param afterDate
     * @param notificationType
     * @return
     */
    public List<Notification> getByUserIdAndAfterTomorrow(Long userId, Date afterDate, NotificationTypeEnum notificationType) {
        return notificationRepository.findByUser_IdAndExpectedSendDateAfterAndNotificationType(userId, afterDate, notificationType);
    }
}
