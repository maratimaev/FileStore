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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bellintegrator.dto.FileInfoView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.FileInfo;
import ru.bellintegrator.entity.Role;
import ru.bellintegrator.service.FileService;
import ru.bellintegrator.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceImplTest {

    @Value("${file-store-folder}")
    private String fileStoreFolder;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Rule
    public TestName testName = new TestName();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private FileInfo fileInfo;
    private UserView uv;

    @Before
    public void setUp() throws IOException {
        if(!testName.getMethodName().contains("checkSuccess")) {
            return;
        }
        this.uv = createUser();
        FileInputStream inputFile = new FileInputStream( "src/test/resources/test.txt");
        MockMultipartFile file = new MockMultipartFile("test", "test.txt", "multipart/form-data", inputFile);
        this.fileInfo = fileService.store(file, this.uv);

        Assert.assertThat(fileInfo.getFilename().equals("test.txt"), is(true));
    }

    @After
    public void clear() {
        if(!testName.getMethodName().contains("checkSuccess")) {
            return;
        }
        fileService.deleteFile(this.fileInfo.getTmpFilename());
        Assert.assertThat(
                new File(Paths.get(fileStoreFolder).toAbsolutePath() + "\\" + this.fileInfo.getTmpFilename()).exists(),
                is(false));
        userService.delete(this.uv.getUsername());
        Assert.assertThat(userService.getUser(this.uv.getUsername()), is(nullValue()));
    }

    @Test
    public void checkSuccessStore() {
        Path rootLocation = Paths.get(fileStoreFolder).toAbsolutePath();
        File f = new File(rootLocation + "\\" + this.fileInfo.getTmpFilename());
        Assert.assertThat(f.exists(), is(true));
    }

    @Test
    public void checkSuccessDownloadFile() {
        Path rootLocation = Paths.get(fileStoreFolder).toAbsolutePath();
        File f = new File(rootLocation + "\\" + fileInfo.getTmpFilename());
        Assert.assertThat(f.exists(), is(true));

        Resource resource = fileService.downloadFile(fileInfo.getTmpFilename());
        Assert.assertThat(resource.getFilename(), is(notNullValue()));
        Assert.assertThat(resource.getFilename().equals(f.getName()), is(true));
    }

    @Test
    public void checkSuccessFindFile() {
        Path rootLocation = Paths.get(fileStoreFolder).toAbsolutePath();
        File f = new File(rootLocation + "\\" + fileInfo.getTmpFilename());
        Assert.assertThat(f.exists(), is(true));

        FileInfoView fiv = fileService.findFile(fileInfo.getTmpFilename());
        Assert.assertThat(fiv.getTmpFilename(), is(notNullValue()));
        Assert.assertThat(fiv.getTmpFilename().equals(f.getName()), is(true));
    }

    @Test
    public void checkSuccessAddDownloadLink() {
        Path rootLocation = Paths.get(fileStoreFolder).toAbsolutePath();
        File f = new File(rootLocation + "\\" + fileInfo.getTmpFilename());
        Assert.assertThat(f.exists(), is(true));

        FileInfoView fiv = fileService.findFile(fileInfo.getTmpFilename());
        Assert.assertThat(fiv.getTmpFilename(), is(notNullValue()));
        Assert.assertThat(fiv.getTmpFilename().equals(f.getName()), is(true));

        List<FileInfoView> list = new ArrayList<>();
        list.add(fiv);
        List<FileInfoView> downloadlist =fileService.addDownloadlink(list);

        FileInfoView dfiv = downloadlist.get(0);
        Assert.assertThat(("http://localhost/files/" + f.getName()).equals(dfiv.getUrl()), is(true));
    }

    @Test
    public void checkFailDeletingFileIfNullTmpFilename() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> tmpFilename can't be null");
        fileService.deleteFile(null);
    }

    @Test
    public void checkFailDownloadingFileIfNullTmpFilename() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> tmpFilename can't be null");
        fileService.downloadFile(null);
    }

    @Test
    public void checkFailStoreIfNullFile() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> file and owner can't be null");
        fileService.store(null, new UserView());
    }

    @Test
    public void checkFailAddDownloadLinkIfNullFiles() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("(Custom) Error -> list of FileInfo can't be null");
        fileService.addDownloadlink(null);
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