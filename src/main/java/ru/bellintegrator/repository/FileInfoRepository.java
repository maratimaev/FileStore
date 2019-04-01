package ru.bellintegrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bellintegrator.entity.FileInfo;
import ru.bellintegrator.entity.User;

import java.util.List;

/**
 * Методы работы с файлами
 */
public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {
    /** Поиск файла
     * @param tmpFilename имя файла с UUID
     * @return entity файла
     */
    FileInfo findByTmpFilename(String tmpFilename);

    /** Поиск файлов пользователя
     * @param user entity пользователя
     * @return список entity файлов
     */
    List<FileInfo> findByUser(User user);

    /** Подсчет числа файлов пользователя
     * @param user entity пользователя
     * @return число файлов
     */
    Integer countFileInfosByUser(User user);
}
