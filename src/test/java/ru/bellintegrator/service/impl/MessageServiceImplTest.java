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
import ru.bellintegrator.dto.MessageView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.Role;
import ru.bellintegrator.entity.User;
import ru.bellintegrator.service.MessageService;
import ru.bellintegrator.service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void checkSuccessMessageCreation() {
        UserView uv = createUser();
        User u = userService.getUser(uv.getUsername());

        String msg = RandomStringUtils.random(25, true, false);
        messageService.createMessage(u, u, msg, 1);
        List<MessageView> messageViews = messageService.getAccessMessagesFrom(uv);
        Assert.assertThat(messageViews.size() > 0, is(true));

        String uuid = messageViews.stream()
                .filter(messageView -> messageView.getMsg().equals(msg))
                .map(MessageView::getMsgUuid)
                .collect(Collectors.toList()).get(0);

        Assert.assertThat(messageService.getMessage(uuid).getMsg().equals(msg), is(true));

        messageService.deleteMessage(uuid);
        userService.delete(uv.getUsername());
    }

    @Test
    public void checkSuccessSavingMessages() {
        UserView from = createUser();
        UserView to = createUser();

        Map<String, String> form = new HashMap<>();
        form.put("askForList-" + to.getUsername(), "test");
        messageService.saveMessages(from, form);

        List<MessageView> messageViews = messageService.getAccessMessagesTo(to);
        Assert.assertThat(messageViews.size() > 0, is(true));

        String uuid = messageViews.stream()
                .filter(messageView -> messageView.getMsg().contains("requests access to list your files"))
                .map(MessageView::getMsgUuid)
                .collect(Collectors.toList()).get(0);

        Assert.assertThat(messageService.getMessage(uuid).getMsg().contains("requests access to list your files"), is(true));

        messageService.deleteMessage(uuid);
        userService.delete(from.getUsername());
        userService.delete(to.getUsername());
    }

    @Test
    public void checkFailMessageCreationIfNullUsers() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> msg, from, to can't be null");
        messageService.createMessage(null, null, "test", 0);
    }

    @Test
    public void checkFailGettingMessageIfNullUuid() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> msgUuid can't be null");
        messageService.getMessage(null);
    }

    @Test
    public void checkFailGettingAccessMessagesFromIfNullUserView() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> userView can't be null");
        messageService.getAccessMessagesFrom(null);
    }

    @Test
    public void checkFailGettingAccessMessagesToIfNullUserView() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> userView can't be null");
        messageService.getAccessMessagesTo(null);
    }

    @Test
    public void checkFailGettingInfoMessagesIfNullUserView() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> userView can't be null");
        messageService.getInfoMessagesTo(null);
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