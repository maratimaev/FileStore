package ru.bellintegrator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Настройка почтовых параметров
 */
@Configuration
public class MailConfig {
    /**
     * Почтовый сервер
     */
    @Value("${spring.mail.host}")
    private String host;

    /**
     * Адрес отправителя
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * Пароль
     */
    @Value("${spring.mail.password}")
    private String password;

    /**
     * Порт сервера
     */
    @Value("${spring.mail.port}")
    private int port;

    /**
     * Протокол отправки писем
     */
    @Value("${spring.mail.protocol}")
    private String protocol;

    /**
     * Признак отладки
     */
    @Value("${mail.debug}")
    private String debug;

    /** Получение бина JavaMailSender
     * @return служба отправки писем
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setPort(port);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);

        return mailSender;
    }
}
