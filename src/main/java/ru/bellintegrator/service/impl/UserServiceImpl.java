package ru.bellintegrator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.DownloadGroup;
import ru.bellintegrator.entity.ListGroup;
import ru.bellintegrator.entity.Role;
import ru.bellintegrator.entity.User;
import ru.bellintegrator.entity.mapper.MapperFacade;
import ru.bellintegrator.repository.UserRepository;

import ru.bellintegrator.service.GroupService;
import ru.bellintegrator.service.MailService;
import ru.bellintegrator.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * {@inheritDoc}
     */
    @Autowired
    private MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private GroupService groupService;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private MailService mailService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void createUser(UserView userView) {
        if (userView == null) {
            throw new RuntimeException("(Custom) Error -> userView can't be null");
        }

        User user = getUser(userView.getUsername());
        if(user == null) {
            user = mapperFacade.map(userView, User.class);
            ListGroup listGroup = groupService.createListGroup();
            DownloadGroup downloadGroup = groupService.createDownloadGroup();

            user.setActive(false);
            user.setRoles(Collections.singleton(Role.USER));
            user.setListGroup(listGroup);
            user.setDownloadGroup(downloadGroup);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationCode(UUID.randomUUID().toString());
            userRepository.save(user);
        }

        user.setActivationCodeCreation(LocalDate.now());
        if (!StringUtils.isEmpty(user.getEmail())) {
            String msg = String.format("Hello, %s! \n To activate your account please visit http://localhost:8080/activate/%s",
                   user.getUsername(), user.getActivationCode());
            mailService.sendEmail(user.getEmail(), "Activation code", msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean activateUser(String activationCode) {
        if (StringUtils.isEmpty(activationCode)) {
            throw new RuntimeException("(Custom) Error -> activationCode can't be null");
        }
        boolean isActivated = false;
        User user = userRepository.findByActivationCode(activationCode);
        if(user != null) {
            user.setActivationCode(null);
            user.setActive(true);
            userRepository.save(user);
            isActivated = true;
        }
        return isActivated;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasCodeExpired(String activationCode) {
        if (StringUtils.isEmpty(activationCode)) {
            throw new RuntimeException("(Custom) Error -> activationCode can't be null");
        }
        boolean isExpired = true;
        User user = userRepository.findByActivationCode(activationCode);
        if(user != null) {
            LocalDate creation = user.getActivationCodeCreation();
            Period diff = Period.between(creation, LocalDate.now());
           if(diff.getDays() < 2) {
               isExpired = false;
           }
        }
        return isExpired;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public boolean hasActivated(UserView userView) {
        if (userView == null) {
            throw new RuntimeException("(Custom) Error -> user can't be null");
        }
        boolean hasActivated = false;
        User user = userRepository.findByUsername(userView.getUsername());
        if(user != null) {
            hasActivated= user.isActive();
        }
        return hasActivated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("(Custom) Error -> username can't be null");
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        if (!user.isActive()) {
            throw new RuntimeException("User disabled! Please activate by email.");
        }
        return mapperFacade.map(user, UserView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getUser(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("(Custom) Error -> username can't be null");
        }
        return userRepository.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserView getUserView(Long id) {
        return  mapperFacade.map(getUser(id), UserView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException(String.format("Error -> Can't find user by id=%s", id));
        }
        return optional.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean userExist(String username){
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("(Custom) Error -> username can't be null");
        }
        return userRepository.existsByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserView> getUserViewList(){
        return mapperFacade.mapAsList(getUserList(), UserView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getUserList(){
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getUsernameListMinus(UserView userView){
        if (userView == null) {
            throw new RuntimeException("(Custom) Error -> userView can't be null");
        }
        List<User> userList = getUserListMinus(userView.getUsername());
        return userList.stream().
                map(User::getUsername).
                collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getUserListMinus(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("(Custom) Error -> username can't be null");
        }
        return userRepository.findAllByUsernameIsNot(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getMemberUsername(Set<User> group) {
        if (group == null) {
            throw new RuntimeException("(Custom) Error -> group can't be null");
        }
        return group.stream().
                map(User::getUsername).
                collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void save(String username, Map<String, String> form, Long userId) {
        if (StringUtils.isEmpty(username) || form == null) {
            throw new RuntimeException("(Custom) Error -> username and form can't be null");
        }
        User user = getUser(userId);
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }
}

