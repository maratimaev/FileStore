package ru.bellintegrator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.bellintegrator.service.MailService;

/**
 * {@inheritDoc}
 */
@Service
public class MailServiceImpl implements MailService {

    /**
     * {@inheritDoc}
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Адрес отправителя писем
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * {@inheritDoc}
     */
    public void sendEmail(String emailTo, String subject, String msg) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(msg);

        mailSender.send(mailMessage);
    }
}
