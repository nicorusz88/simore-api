package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Message;
import ar.com.simore.simoreapi.entities.User;
import ar.com.simore.simoreapi.entities.resources.MessageResource;
import ar.com.simore.simoreapi.entities.resources.UserResource;
import ar.com.simore.simoreapi.repositories.MessageRepository;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService extends BaseService<MessageRepository, Message> {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected MessageRepository getRepository() {
        return messageRepository;
    }

    /**
     * Sends a message form user 1 to user 2
     *
     * @param message
     * @return
     */
    public MessageResource sendMessage(final Message message) {
        message.setSendDate(DateUtils.getCurrentDate());
        final User from = userRepository.findOne(message.getFrom().getId());
        final User to = userRepository.findOne(message.getTo().getId());
        message.setFrom(from);
        message.setTo(to);
        messageRepository.save(message);
        return convertMessageToMessageResource(message);
    }

    /**
     * Gets the conversation between user 1 and user 2
     *
     * @param user1Id
     * @param user2Id
     * @return
     */
    public List<MessageResource> getConversation(Long user1Id, Long user2Id) {
        final List<MessageResource> messageResources = new ArrayList<>();
        final List<Message> messages = messageRepository.findByFrom_IdAndTo_IdOrFrom_IdAndTo_IdOrderBySendDateAsc(user1Id, user2Id, user2Id, user1Id);
        messages.forEach(message -> messageResources.add(convertMessageToMessageResource(message)));
        return messageResources;

    }

    private MessageResource convertMessageToMessageResource(Message message) {
        final MessageResource messageResource = new MessageResource();
        messageResource.setId(message.getId());
        messageResource.setRead(message.isRead());
        messageResource.setText(message.getText());
        messageResource.setSendDate(message.getSendDate());
        messageResource.setFrom(convertUserToUserResource(message.getFrom()));
        messageResource.setTo(convertUserToUserResource(message.getTo()));
        return messageResource;

    }

    private UserResource convertUserToUserResource(User user) {
        final UserResource userResource = new UserResource();
        userResource.setAvatar(user.getAvatar());
        userResource.setUserName(user.getUserName());
        userResource.setFirstName(user.getFirstName());
        userResource.setLastName(user.getLastName());
        userResource.setId(user.getId());
        return userResource;
    }

    /**
     * Sets the message as read
     *
     * @param messageId
     * @return
     */
    public MessageResource setAsRead(Long messageId) {
        final Message message = messageRepository.findOne(messageId);
        message.setRead(true);
        messageRepository.save(message);
        return convertMessageToMessageResource(message);
    }

    public List<MessageResource> getSent(Long userId) {
        final List<MessageResource> messageResources = new ArrayList<>();
        final List<Message> messages = messageRepository.findByFrom_IdOrderBySendDateAsc(userId);
        messages.forEach(message -> messageResources.add(convertMessageToMessageResource(message)));
        return messageResources;
    }

    public List<MessageResource> getReceived(Long userId) {        final List<MessageResource> messageResources = new ArrayList<>();
        final List<Message> messages = messageRepository.findByTo_IdOrderBySendDateAsc(userId);
        messages.forEach(message -> messageResources.add(convertMessageToMessageResource(message)));
        return messageResources;

    }

    public Integer getUnread(Long userId) {
        final List<Message> messages = messageRepository.findByTo_IdAndIsRead(userId, false);
        return messages.size();
    }
}
