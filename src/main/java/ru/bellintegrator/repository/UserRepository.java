package ru.bellintegrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bellintegrator.entity.Role;
import ru.bellintegrator.entity.User;

import java.util.List;

/**
 * Методы работы с пользователями
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /** Поиск пользователя по имени
     * @param username имя пользователя
     * @return entity пользователя
     */
    User findByUsername(String username);

    /** Поиск пользователей кроме username
     * @param username имя пользователя
     * @return список entity пользователей
     */
    List<User> findAllByUsernameIsNot(String username);

    /** Проверка на существование пользователя
     * @param username имя пользователя
     * @return правда/ложь
     */
    boolean existsByUsername(String username);

    /** Проверка на наличие роли у пользователя
     * @param username имя пользователя
     * @param role роль
     * @return правда/ложь
     */
    boolean existsByUsernameAndRolesContains(String username, Role role);

    /** Поиск пользователя по коду активации
     * @param acivationCode код активации
     * @return entity пользователя
     */
    User findByActivationCode(String acivationCode);
}
