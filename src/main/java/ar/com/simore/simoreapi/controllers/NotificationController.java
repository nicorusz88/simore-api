package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController extends BaseController<NotificationService, Notification> {

    @Autowired
    private NotificationService notificationService;

    @Override
    NotificationService getService() {
        return notificationService;
    }

    /** Sets the notification read date, indicating that the user read it
     * @param id  notification id
     * @return
     */
    @GetMapping("/set-read/{id}")
    public ResponseEntity<Notification> addNotificationToTreatment(@PathVariable("id") Long id){
        return ResponseEntity.ok(notificationService.setRead(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable("id") Long userId){
        return ResponseEntity.ok(notificationService.getByUserId(userId));
    }
}
