package ru.bellintegrator.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.bellintegrator.entity.FileInfo;
import ru.bellintegrator.entity.User;
import ru.bellintegrator.service.FileService;
import ru.bellintegrator.service.UserService;

import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DownloadFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    private final String USERNAME = "test";

    @WithUserDetails(USERNAME)
    @Test
    public void shouldReturnListFiles() throws Exception {
        User user = userService.getUser(USERNAME);
        String filename = RandomStringUtils.random(8, true, false) + ".txt";
        FileInfo tempFile = new FileInfo(filename, "UUID-"+filename, 123L, user, 0);

        fileService.saveToDb(tempFile);

        this.mockMvc.perform(get("/files"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("My files")))
                .andExpect(view().name("multipartfile/listFiles"))
                .andExpect(model().attribute(
                        "userfiles", hasItem(hasProperty("filename", is(filename)))));

        fileService.deleteFromDb(tempFile);

        this.mockMvc.perform(get("/files"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("My files")))
                .andExpect(view().name("multipartfile/listFiles"))
                .andExpect(model().attribute(
                        "userfiles", is(new ArrayList<>())));
    }
}