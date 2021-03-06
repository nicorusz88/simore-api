package ar.com.simore.simoreapi.repositories;

import ar.com.simore.simoreapi.entities.Appointment;
import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findByExpectedSendDateBeforeAndActualSendDateIsNullAndReadDateIsNullAndNotificationType(Date expectedSendDate, NotificationTypeEnum notificationType);

    List<Notification> findFirst20ByUser_IdAndActualSendDateIsNotNullOrderByActualSendDateDesc(Long userId);

    List<Notification> findByUser_IdAndActualSendDateIsNullAndNotificationTypeOrderByExpectedSendDate(Long userId, NotificationTypeEnum notificationType);

    Notification findFirstByReferenceIdAndNotificationTypeAndActualSendDateIsNotNullOrderByActualSendDateDesc(long referenceId, NotificationTypeEnum notificationType );

    List<Notification> findByUser_IdAndExpectedSendDateBetweenAndNotificationType(Long userId, Date currentDateFirstHour, Date currentDateLastHour, NotificationTypeEnum notificationType);

    List<Notification>  findByUser_IdAndExpectedSendDateAfterAndNotificationType(Long userId, Date tomorrowDateLastHour, NotificationTypeEnum notificationType);

    List<Notification> findFirst10ByUser_IdAndActualSendDateIsNotNullAndNotificationTypeOrderByActualSendDateDesc(Long userId, NotificationTypeEnum notificationType);
}
