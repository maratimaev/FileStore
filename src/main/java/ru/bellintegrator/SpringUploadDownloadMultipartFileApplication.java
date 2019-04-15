package ru.bellintegrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.bellintegrator.service.FileService;

/**
 * Старт приложения
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringUploadDownloadMultipartFileApplication implements CommandLineRunner {

    @Autowired
    FileService fileService;

    /** Точка старта приложения
     * @param args аргументы строки
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringUploadDownloadMultipartFileApplication.class, args);
    }

    /** Подготовка необходимых для работы ресурсов
     * @param args аргументы строки
     */
    @Override
    public void run(String... args) {
        fileService.init();
    }
}
