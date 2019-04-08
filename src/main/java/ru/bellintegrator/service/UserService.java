package ru.bellintegrator.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис работы с пользователем
 */
public interface UserService extends UserDetailsService{
    /** Создание пользователя
     * @param userView dto пользователя
     */
    void            createUser          (UserView userView);

    /** Активация пользователя
     * @param activationCode код активации
     * @return правда/ложь
     */
    boolean         activateUser        (String activationCode);

    /** Проверка активирован ли пользователь
     * @param userView модель пользователя
     * @return правда/ложь
     */
    boolean         hasActivated        (UserView userView);

    /** Проверка на истечение срока активации кода
     * @param acivationCode код
     * @return правда/ложь
     */
    boolean         hasCodeExpired(String acivationCode);

    /** Получение списка пользователей
     * @return список dto пользователей
     */
    List<UserView>  getUserViewList     ();

    /** Получение списка пользователей
     * @return список entity пользователй
     */
    List<User>      getUserList         ();

    /** Получение списка имен пользователей кроме userView
     * @param userView dto пользователя
     * @return список имен пользователей
     */
    List<String>    getUsernameListMinus(UserView userView);

    /** Получение списка пользователей кроме username
     * @param username имя пользователя
     * @return список entity пользователй
     */
    List<User>      getUserListMinus    (String username);

    /** Получение dto пользователя по id
     * @param id пользователя
     * @return dto пользователя
     */
    UserView        getUserView         (Long id);

    /** Получение пользователя по имени
     * @param username имя
     * @return entity пользователя
     */
    User            getUser             (String username);

    /** Получение пользователя по id
     * @param id пользователя
     * @return entity пользователя
     */
    User            getUser             (Long id);

    /** Получение списка имен членов группы
     * @param group группа
     * @return имена членов группы
     */
    Set<String>     getMemberUsername   (Set<User> group);

    /** Проверка на существование пользователя
     * @param username имя пользователя
     * @return правда/ложь
     */
    boolean         userExist           (String username);

    /** Сохранение пользователя
     * @param username имя
     * @param form данные со страницы
     * @param userId id пользователя
     */
    void            save                (String username, Map<String, String> form, Long userId);
}
