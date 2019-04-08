package ru.bellintegrator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.DownloadGroup;
import ru.bellintegrator.entity.ListGroup;
import ru.bellintegrator.entity.Message;
import ru.bellintegrator.entity.User;
import ru.bellintegrator.repository.DownloadGroupRepository;
import ru.bellintegrator.repository.ListGroupRepository;
import ru.bellintegrator.service.GroupService;
import ru.bellintegrator.service.MessageService;
import ru.bellintegrator.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@inheritDoc}
 */
@Service
public class GroupServiceImpl implements GroupService {

    /**
     * {@inheritDoc}
     */
    @Autowired
    private UserService userService;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private MessageService messageService;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private ListGroupRepository listGroupRepository;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private DownloadGroupRepository downloadGroupRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ListGroup createListGroup() {
        ListGroup lg = new ListGroup();
        lg.setMembers(new HashSet<>());
        listGroupRepository.save(lg);
        return lg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public DownloadGroup createDownloadGroup() {
        DownloadGroup dg = new DownloadGroup();
        dg.setMembers(new HashSet<>());
        downloadGroupRepository.save(dg);
        return dg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getListGroup(UserView userView){
        if (userView == null) {
            throw new RuntimeException("(Custom) Error -> userView can't be null");
        }
        User user = userService.getUser(userView.getUsername());
        return getGroup(user.getListGroup().getMembers());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getDownloadGroup(UserView userView){
        if (userView == null) {
            throw new RuntimeException("(Custom) Error -> userView can't be null");
        }
        User user = userService.getUser(userView.getUsername());
        return getGroup(user.getDownloadGroup().getMembers());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveUserGroups(UserView userView, Map<String, String> form){
        if (userView == null || form == null) {
            throw new RuntimeException("(Custom) Error -> userView and form can't be null");
        }
        List<User> allMinusUser = userService.getUserListMinus(userView.getUsername());
        User user = userService.getUser(userView.getUsername());

        ListGroup listGroup = user.getListGroup();
        listGroup.setMembers(new HashSet<>());
        listGroupRepository.save(listGroup);

        DownloadGroup downloadGroup = user.getDownloadGroup();
        downloadGroup.setMembers(new HashSet<>());
        downloadGroupRepository.save(downloadGroup);

        for (User u : allMinusUser) {
            if (form.keySet().contains("list-"+u.getUsername())) {
                listGroup.getMembers().add(u);
            }
            if (form.keySet().contains("download-"+u.getUsername())) {
                downloadGroup.getMembers().add(u);
                listGroup.getMembers().add(u);
            }
        }
        listGroupRepository.save(listGroup);
        downloadGroupRepository.save(downloadGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveUserGroupByMessage(UserView userView, String msgUuid) {
        if (StringUtils.isEmpty(msgUuid) || userView == null) {
            throw new RuntimeException("(Custom) Error -> msgUuid and userView can't be null");
        }
        User user = userService.getUser(userView.getUsername());
        Message message = messageService.getMessage(msgUuid);
        User addedUser = userService.getUser(message.getFrom().getUsername());

        switch (message.getCode()) {
            case 1:
                ListGroup listGroup = user.getListGroup();
                listGroup.getMembers().add(addedUser);
                listGroupRepository.save(listGroup);

                messageService.createMessage(user, addedUser, "User " + user.getUsername() + " gives you access to list files", 0);
                break;
            case 2:
                ListGroup lg = user.getListGroup();
                lg.getMembers().add(addedUser);
                listGroupRepository.save(lg);

                DownloadGroup dg = user.getDownloadGroup();
                dg.getMembers().add(addedUser);
                downloadGroupRepository.save(dg);

                messageService.createMessage(user, addedUser, "User " + user.getUsername() + " gives you access to download files", 0);
                break;
        }
        messageService.deleteMessage(message.getMsgUuid());
    }

    /**
     * {@inheritDoc}
     */
    private Set<String> getGroup(Set<User> group){
        return userService.getMemberUsername(group);
    }
}
