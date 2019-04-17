package ru.bellintegrator.service;

import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.DownloadGroup;
import ru.bellintegrator.entity.ListGroup;

import java.util.Map;
import java.util.Set;

/**
 * Сервис работы с группами
 */
public interface GroupService {
    /** Создание группы на просмотр файлов
     * @return entity группы
     */
    ListGroup createListGroup();

    /** Создание группы на скачивание файлов
     * @return entity группы
     */
    DownloadGroup createDownloadGroup();

    /** Получеие членов группы List пользователя
     * @param userView dto пользователя
     * @return члены группы
     */
    Set<String> getListGroup(UserView userView);

    /** Получеие членов группы Download пользователя
     * @param userView dto пользователя
     * @return члены группы
     */
    Set<String> getDownloadGroup(UserView userView);

    /** Сохранение групп пользователя
     * @param userView dto пользователя
     * @param form данные со страницы
     */
    void saveUserGroups(UserView userView, Map<String, String> form);

    /** Сохранение групп прользователя по запросу от другого пользователя
     * @param userView dto пользователя
     * @param msgUuid Uuid запроса
     * @return UUID отетного уведомляющего сообщения
     */
    String saveUserGroupByMessage(UserView userView, String msgUuid);

    /** Удаление List группы пользователя
     * @param listGroup группа пользоватедей с возможностью просмотра файлов пользователя
     */
    void deleteListGroup(ListGroup listGroup);

    /** Удаление пользователй из группы просмотра
     * @param listGroup группа пользоватедей с возможностью просмотра файлов пользователя
     */
    void clearListGroup(ListGroup listGroup);

    /** Удаление Download группы пользователя
     * @param downloadGroup группа пользоватедей с возможностью скачивания файлов пользователя
     */
    void deleteDownloadGroup(DownloadGroup downloadGroup);

    /** Удаление пользователей из группы скачивания файлов
     * @param downloadGroup группа пользоватедей с возможностью скачивания файлов пользователя
     */
    void clearDownloadGroup(DownloadGroup downloadGroup);
}
