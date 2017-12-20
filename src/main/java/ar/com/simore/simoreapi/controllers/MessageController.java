package ar.com.simore.simoreapi.controllers;


import ar.com.simore.simoreapi.entities.Message;
import ar.com.simore.simoreapi.entities.resources.MessageResource;
import ar.com.simore.simoreapi.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController extends BaseController<MessageService, Message> {

    @Autowired
    private MessageService messageService;

    @Override
    MessageService getService() {
        return messageService;
    }

    /** Sends a message form user 1 to user 2
     * @param message
     * @return
     */
    @PostMapping("/send")
    public ResponseEntity<MessageResource> sendMessage(@Valid @RequestBody final Message message){
        return ResponseEntity.ok(messageService.sendMessage(message));
    }

    /** Sets a message as read
     * @param messageId
     * @return
     */
    @GetMapping("/set-read/{messageId}")
    public ResponseEntity<MessageResource> setMessageAsRead(@PathVariable final Long messageId){
        return ResponseEntity.ok(messageService.setAsRead(messageId));
    }

    /** Gets the conversation between 2 users, and sendds is ordered by date ascending
     * @param user1Id
     * @param user2Id
     * @return
     */
    @GetMapping("/conversation/{user1Id}/{user2Id}")
    public ResponseEntity<List<MessageResource>> getConversation(@PathVariable("user1Id") final Long user1Id, @PathVariable("user2Id") final Long user2Id ){
        return ResponseEntity.ok(messageService.getConversation(user1Id, user2Id));
    }

    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<MessageResource>> getSent(@PathVariable final Long userId){
        return ResponseEntity.ok(messageService.getSent(userId));
    }

    @GetMapping("/received/{userId}")
    public ResponseEntity<List<MessageResource>> getReceived(@PathVariable final Long userId){
        return ResponseEntity.ok(messageService.getReceived(userId));
    }

    @GetMapping("/unread/{userId}")
    public ResponseEntity<Integer> getUnread(@PathVariable final Long userId){
        return ResponseEntity.ok(messageService.getUnread(userId));
    }
}
