package ru.bellintegrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bellintegrator.entity.Message;
import ru.bellintegrator.entity.User;

import java.util.List;

/**
 * Методы работс с сообщениями
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    /** Поиск сообщений по адресату и кодам
     * @param user entity пользователя
     * @param codes список кодов
     * @return список entity сообщений
     */
    List<Message> findByToAndCodeIsIn(User user, Integer[] codes);

    /** Поиск сообщений по отправителю и кодам
     * @param user entity пользователя
     * @param codes список кодов
     * @return список entity сообщений
     */
    List<Message> findByFromAndCodeIsIn(User user, Integer[] codes);

    /** Удаление сообщения по UUID
     * @param msgUuid UUID сообщения
     */
    void deleteByMsgUuid(String msgUuid);

    /** Поиск сообщения по UUID
     * @param msgUuid UUID сообщения
     * @return entity сообщения
     */
    Message findByMsgUuid(String msgUuid);
}
