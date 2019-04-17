package ru.bellintegrator.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.Role;
import ru.bellintegrator.entity.User;
import ru.bellintegrator.service.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    private UserView testUserView;

    @Autowired
    private UserService userService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public TestName testName = new TestName();

    @Before
    public void createTestUser() {
        if(testName.getMethodName().contains("checkFail")) {
            return;
        }
        this.testUserView = new UserView(
                RandomStringUtils.random(8, true, false),
                RandomStringUtils.random(8, true, true),
                Arrays.stream(Role.values()).filter(role -> role.name().equals("USER")).collect(Collectors.toSet())
        );
        userService.createUser(this.testUserView);
    }

    @After
    public void deleteTestUser() {
        if(testName.getMethodName().contains("checkFail")) {
            return;
        }
        userService.delete(this.testUserView.getUsername());
    }

    @Test
    public void checkSuccessUserCreation() {
        Assert.assertThat(userService.userExist(this.testUserView.getUsername()), is(true));

        User checkUser = userService.getUser(this.testUserView.getUsername());
        Assert.assertThat(checkUser.getUsername().equals(this.testUserView.getUsername()), is(true));
        Assert.assertThat(checkUser.getId(), is(notNullValue()));
        Assert.assertThat(checkUser.getActivationCode().isEmpty(), is(false));
        Assert.assertThat(checkUser.isActive(),is(false));
    }

    @Test
    public void checkSuccessUserActivation() {
        User beforeActivationUser = userService.getUser(this.testUserView.getUsername());

        Assert.assertThat(beforeActivationUser.getActivationCode(), is(notNullValue()));
        Assert.assertThat(userService.hasActivated(this.testUserView), is(false));
        userService.activateUser(beforeActivationUser.getActivationCode());

        User afterActivationUser = userService.getUser(this.testUserView.getUsername());
        Assert.assertThat(afterActivationUser.getActivationCode(), is(nullValue()));
        Assert.assertThat(userService.hasActivated(this.testUserView), is(true));
    }

    @Test
    public void checkSuccessUserListMinusUser() {
        List<String> fullList = userService.getUserViewList().stream().map(UserView::getUsername).collect(Collectors.toList());

        Assert.assertThat(fullList.contains(this.testUserView.getUsername()), is(true));
        List<String> cuttedList = userService.getUsernameListMinus(this.testUserView);
        Assert.assertThat(cuttedList.contains(this.testUserView.getUsername()), is(false));
    }

    @Test
    public void checkIfCodeExpired() {
        User expiredCodeUser = userService.getUser(this.testUserView.getUsername());
        expiredCodeUser.setActivationCodeCreation(LocalDate.now().minusDays(2));
        userService.saveToDb(expiredCodeUser);
        Assert.assertThat(userService.hasCodeExpired(expiredCodeUser.getActivationCode()), is(true));
    }

    @Test
    public void checkSuccessUserRetrieving() {
        String testUsername = this.testUserView.getUsername();
        User activatedUser = userService.getUser(testUsername);
        userService.activateUser(activatedUser.getActivationCode());

        UserDetails userDetails = userService.loadUserByUsername(testUsername);
        Assert.assertThat(userDetails.getUsername().equals(testUsername), is(true));

        User user = userService.getUser(testUsername);
        Assert.assertThat(user.getUsername().equals(testUsername), is(true));
        Assert.assertThat(userService.getUser(user.getId()).getUsername().equals(user.getUsername()), is(true));
        Assert.assertThat(userService.getUserView(user.getId()).getUsername().equals(testUsername), is(true));
    }

    @Test
    public void checkFailUserActivationIfNullCode() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> activationCode can't be null");
        userService.activateUser(null);
    }

    @Test
    public void checkFailUserActivationIfWrongCode() {
        boolean isActivated = userService.activateUser(RandomStringUtils.random(20, true, true));
        Assert.assertThat(isActivated, is(false));
    }

    @Test
    public void checkFailUserRetrievingIfNullUsername() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> username can't be null");
        userService.loadUserByUsername(null);
    }

    @Test
    public void checkFailUserLoadingIfWrongUsername() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("User not found!");
        userService.loadUserByUsername(RandomStringUtils.random(8, true, true));
    }

    @Test
    public void checkFailUserGettingIfWrongUsername() {
        User user = userService.getUser(RandomStringUtils.random(8, true, true));
        Assert.assertThat(user, is(nullValue()));
    }

    @Test
    public void checkFailUserViewGettingIfWrongId() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> Can't find user by id=0");
        userService.getUserView(0L);
    }

    @Test
    public void checkFailUserViewListGettingIfNullUserView() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> userView can't be null");
        userService.getUsernameListMinus(null);
    }

    @Test
    public void checkFailUserCreation() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> userView can't be null");
        userService.createUser(null);
    }
}