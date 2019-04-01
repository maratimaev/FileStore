package ru.bellintegrator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Настройка страницы аутентификации
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /** Регистрация контроллера для /login
     * @param registry регистрация контроллера
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("multipartfile/login");
    }
}

