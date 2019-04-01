package ru.bellintegrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bellintegrator.service.FileService;

@SpringBootApplication
public class SpringUploadDownloadMultipartFileApplication implements CommandLineRunner {

    @Autowired
    FileService fileService;

    public static void main(String[] args) {
        SpringApplication.run(SpringUploadDownloadMultipartFileApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fileService.init();
    }
}
