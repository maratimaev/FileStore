package ru.bellintegrator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.bellintegrator.interceptor.AddMessagesResponseInterceptor;

/**
 * Настройка страницы аутентификации
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private AddMessagesResponseInterceptor addMessagesResponseInterceptor;

    /** Регистрация контроллера для /login
     * @param registry регистрация контроллера
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("multipartfile/login");
    }

    /** Добавления перехватчика для вывода сообщений пользователю
     * @param registry  регистрация
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(addMessagesResponseInterceptor);
    }
}

