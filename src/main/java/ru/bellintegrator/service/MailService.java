package ru.bellintegrator.service;

/**
 * Сервис работы с электронными писмьмами
 */
public interface MailService {
    /** Отправка письма
     * @param emailTo адресат
     * @param subject тема
     * @param msg тело письма
     */
    void sendEmail(String emailTo, String subject, String msg);
}
