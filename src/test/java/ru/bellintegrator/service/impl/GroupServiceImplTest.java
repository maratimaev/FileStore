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
import ru.bellintegrator.entity.Role;
import ru.bellintegrator.service.GroupService;
import ru.bellintegrator.service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupServiceImplTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Test
    public void checkSuccessSavingUserGroups() {
        UserView uv = createUser();
        UserView member = createUser();

        Map<String, String> form = new HashMap<>();
        form.put("download-"+member.getUsername(), "test");

        groupService.saveUserGroups(uv, form);

        Set<String> downLoadGroup = groupService.getDownloadGroup(uv);
        Assert.assertThat(downLoadGroup.contains(member.getUsername()), is(true));


        userService.delete(member.getUsername());
        userService.delete(uv.getUsername());
        groupService.deleteDownloadGroup(uv);
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
}