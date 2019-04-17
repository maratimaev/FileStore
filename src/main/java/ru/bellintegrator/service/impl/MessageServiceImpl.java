package ru.bellintegrator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.bellintegrator.dto.MessageView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.Message;
import ru.bellintegrator.entity.User;
import ru.bellintegrator.entity.mapper.MapperFacade;
import ru.bellintegrator.repository.MessageRepository;
import ru.bellintegrator.repository.UserRepository;
import ru.bellintegrator.service.MessageService;
import ru.bellintegrator.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
@Service
public class MessageServiceImpl implements MessageService {

    /**
     * {@inheritDoc}
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private UserService userService;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private MessageRepository messageRepository;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveMessages(UserView userView, Map<String, String> form) {
        if (userView == null || form == null) {
            throw new RuntimeException("(Custom) Error -> userView and form can't be null");
        }
        List<User> allMinusUser = userRepository.findAllByUsernameIsNot(userView.getUsername());
        User user = userService.getUser(userView.getUsername());

        for (User recipient : allMinusUser) {
            if (form.keySet().contains("askForList-"+recipient.getUsername())) {
                createMessage(user, recipient, "User " + user.getUsername() + " requests access to list your files", 1);
            }
            if (form.keySet().contains("askForDownload-"+recipient.getUsername())) {
                createMessage(user, recipient, "User " + user.getUsername() + " requests access to download your files", 2);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<MessageView> getAccessMessagesTo(UserView userView) {
        if (userView == null) {
            throw new RuntimeException("(Custom) Error -> userView can't be null");
        }
        User user = userService.getUser(userView.getUsername());
        List<Message> messages = messageRepository.findByToAndCodeIsIn(user, new Integer[]{1,2});
        return mapperFacade.mapAsList(messages, MessageView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<MessageView> getInfoMessagesTo(UserView userView) {
        if (userView == null) {
            throw new RuntimeException("(Custom) Error -> userView can't be null");
        }
        User user = userService.getUser(userView.getUsername());
        List<Message> messages = messageRepository.findByToAndCodeIsIn(user, new Integer[]{0});
        return mapperFacade.mapAsList(messages, MessageView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<MessageView> getAccessMessagesFrom(UserView userView) {
        if (userView == null) {
            throw new RuntimeException("(Custom) Error -> userView can't be null");
        }
        User user = userService.getUser(userView.getUsername());
        List<Message> messages = messageRepository.findByFromAndCodeIsIn(user, new Integer[]{1,2});
        return mapperFacade.mapAsList(messages, MessageView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String createMessage(User from, User to, String msg, Integer code) {
        if (StringUtils.isEmpty(msg) || from == null || to == null) {
            throw new RuntimeException("(Custom) Error -> msg, from, to can't be null");
        }
        String uuid = UUID.randomUUID().toString();
        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setMsg(msg);
        message.setCode(code);
        message.setMsgUuid(uuid);
        messageRepository.save(message);
        return uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteMessage(String msgUuid) {
        try {
            messageRepository.deleteByMsgUuid(msgUuid);
        } catch (Exception e) {
            throw new RuntimeException(String.format("(Custom) Wrong message msgUuid = %s", msgUuid), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void declineAccess(String msgUuid) {
        if (StringUtils.isEmpty(msgUuid)) {
            throw new RuntimeException("(Custom) Error -> msgUuid can't be null");
        }
        Message message = getMessage(msgUuid);
        createMessage(message.getTo(), message.getFrom(), "User " + message.getTo().getUsername() + " decline access to list files", 0);
        deleteMessage(msgUuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Message getMessage(String msgUuid) {
        if (StringUtils.isEmpty(msgUuid)) {
            throw new RuntimeException("(Custom) Error -> msgUuid can't be null");
        }
        Message message;
        try {
            message = messageRepository.findByMsgUuid(msgUuid);
        } catch (Exception e) {
            throw new RuntimeException(String.format("(Custom) Wrong message msgUuid = %s", msgUuid), e);
        }
        return message;
    }
}
