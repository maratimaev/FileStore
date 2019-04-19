package ru.bellintegrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Старт Spring приложения
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringUploadDownloadMultipartFileApplication extends SpringBootServletInitializer {

    /** Точка старта приложения
     * @param args аргументы строки
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringUploadDownloadMultipartFileApplication.class, args);
    }
}
