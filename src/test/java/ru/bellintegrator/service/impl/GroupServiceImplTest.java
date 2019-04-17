package ru.bellintegrator.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.DownloadGroup;
import ru.bellintegrator.entity.ListGroup;
import ru.bellintegrator.entity.Role;
import ru.bellintegrator.service.GroupService;
import ru.bellintegrator.service.MessageService;
import ru.bellintegrator.service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupServiceImplTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private MessageService messageService;

    @Test
    public void checkSuccessSavingUserGroups() {
        UserView uv = createUser();
        UserView member = createUser();

        Map<String, String> form = new HashMap<>();
        form.put("download-"+member.getUsername(), "test");

        groupService.saveUserGroups(uv, form);

        Set<String> downLoadGroup = groupService.getDownloadGroup(uv);
        Assert.assertThat(downLoadGroup.contains(member.getUsername()), is(true));

        deleteUserAndGroup(uv, member);

        Assert.assertThat(userService.getUser(uv.getUsername()), is(nullValue()));
        Assert.assertThat(userService.getUser(member.getUsername()), is(nullValue()));
    }

    @Test
    public void checkSuccessSavingUserGroupByMessage() {
        UserView addedUserView = createUser();
        UserView uv = createUser();

        String msg = RandomStringUtils.random(25, true, false);
        String uuid = messageService.createMessage(
                userService.getUser(addedUserView.getUsername()),
                userService.getUser(uv.getUsername()),
                msg,
                1
        );
        String uuidResponce = groupService.saveUserGroupByMessage(uv, uuid);

        Set<String> listGroup = groupService.getListGroup(uv);
        Assert.assertThat(listGroup.contains(addedUserView.getUsername()), is(true));

        messageService.deleteMessage(uuidResponce);
        deleteUserAndGroup(uv, addedUserView);

        Assert.assertThat(userService.getUser(uv.getUsername()), is(nullValue()));
        Assert.assertThat(userService.getUser(addedUserView.getUsername()), is(nullValue()));
    }

    @Test
    public void checkFailGettingListGroupIfNullUserView() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> userView can't be null");
        groupService.getListGroup(null);
    }

    @Test
    public void checkFailGettingDownloadGroupIfNullUserView() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> userView can't be null");
        groupService.getDownloadGroup(null);
    }

    @Test
    public void checkFailSavingUserGroupIfNullUserView() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> userView and form can't be null");
        Map<String, String> form = new HashMap<>();
        groupService.saveUserGroups(null, form);
    }

    @Test
    public void checkFailSavingUserGroupIfNullForm() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> userView and form can't be null");
        groupService.saveUserGroups(new UserView(), null);
    }

    @Test
    public void checkFailSavingUserGroupByMessageIfNullUserView() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> msgUuid and userView can't be null");
        groupService.saveUserGroupByMessage(null, "test");
    }

    @Test
    public void checkFailSavingUserGroupByMessageIfNullUuid() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> msgUuid and userView can't be null");
        groupService.saveUserGroupByMessage(new UserView(), "");
    }

    private UserView createUser() {
        UserView uv = new UserView(
                RandomStringUtils.random(8, true, false),
                RandomStringUtils.random(8, true, true),
                Arrays.stream(Role.values()).filter(role -> role.name().equals("USER")).collect(Collectors.toSet())
        );
        userService.createUser(uv);
        return uv;
    }

    private void deleteUserAndGroup(UserView uv, UserView member) {
        ListGroup lgUv = userService.getUser(uv.getUsername()).getListGroup();
        DownloadGroup dgUv = userService.getUser(uv.getUsername()).getDownloadGroup();
        ListGroup lgMember = userService.getUser(member.getUsername()).getListGroup();
        DownloadGroup dgMember = userService.getUser(member.getUsername()).getDownloadGroup();

        groupService.clearDownloadGroup(dgUv);
        groupService.clearListGroup(lgUv);
        userService.delete(member.getUsername());
        userService.delete(uv.getUsername());
        groupService.deleteListGroup(lgUv);
        groupService.deleteDownloadGroup(dgUv);
        groupService.deleteListGroup(lgMember);
        groupService.deleteDownloadGroup(dgMember);
    }
}