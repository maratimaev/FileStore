package ru.bellintegrator.service;

import ru.bellintegrator.dto.MessageView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.Message;
import ru.bellintegrator.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Сервис работы с сообщениями
 */
public interface MessageService {
    /** Сохранение сообщения
     * @param userView dto отправителя
     * @param form данные со страницы
     */
    void saveMessages(UserView userView, Map<String, String> form);

    /** Получение списка сообщений на открытие доступа адресованных userView
     * @param userView dto адресата
     * @return список dto сообщений
     */
    List<MessageView> getAccessMessagesTo(UserView userView);

    /** Получение списка уведомляющих сообщений адресованных userView
     * @param userView dto адресата
     * @return список dto сообщений
     */
    List<MessageView> getInfoMessagesTo(UserView userView);

    /** Получение списка сообщений на открытие доступа отправленных от userView
     * @param userView dto отправителя
     * @return список dto сообщений
     */
    List<MessageView> getAccessMessagesFrom(UserView userView);

    /** Создание сообщения
     * @param from отправитель
     * @param to адресат
     * @param msg тело сообщения
     * @param code код сообщения (0 - уведомление, 1 - доступ на просмотр, 2 - доступ на скачивание)
     */
    void createMessage(User from, User to, String msg, Integer code);

    /** Удаление сообщения
     * @param msgUuid Uuid сообщения
     */
    void deleteMessage(String msgUuid);

    /** Отклонение запрашиваемого доступа
     * @param msgUuid uuid сообщения
     */
    void declineAccess(String msgUuid);

    /** Получение сообщения по uuid
     * @param msgUuid uuid сообщения
     * @return entity сообщения
     */
    Message getMessage(String msgUuid);
}
