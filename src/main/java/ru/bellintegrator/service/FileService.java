package ru.bellintegrator.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.bellintegrator.dto.FileInfoView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.FileInfo;

import java.util.List;

/**
 * Сервис работы с файлами
 */
public interface FileService {
    /** Поиск файла по имени и Uuid
     * @param tmpFilename имя файла плюс Uuid
     * @return dto файла
     */
    FileInfoView findFile(String tmpFilename);

    /** Получение файлов пользователя
     * @param username имя пользователя
     * @return список dto файлов
     */
    List<FileInfoView> getUserFiles(String username);

    /** Получение доступных на просмотр файлов других пользователей
     * @param username имя пользователя
     * @return список dto файлов
     */
    List<FileInfoView> getListableSharedFiles(String username);

    /** Получение доступных на скачивание файлов других пользователей
     * @param username имя пользователя
     * @return список dto файлов@return
     */
    List<FileInfoView> getDownloadableSharedFiles(String username);

    /** Получение списка всех файлов
     * @return список dto файлов
     */
    List<FileInfoView> getAllFiles();

    /** Добавление ссылки на скачивание
     * @param files список dto файлов
     * @return список dto файлов
     */
    List<FileInfoView> addDownloadlink(List<FileInfoView> files);

    /** Получение количества файлов пользователя
     * @param userView dto пользователя
     * @return количество файлов
     */
    Integer countFiles(UserView userView);

    /** Генерация UUID и сохранение файла
     * @param file  файл
     * @param owner dto владельца
     * @return entity файла
     */
    FileInfo store(MultipartFile file, UserView owner);

    /** Сохранение entity файла в БД
     * @param fileInfo entity файла
     */
    void saveToDb(FileInfo fileInfo);

    /** Скачивание файла
     * @param tmpFilename имя файла c Uuid
     * @return скачиваемый ресурс
     */
    Resource downloadFile(String tmpFilename);

    /** Удаление файла
     * @param tmpFilename имя файла с Uuid
     */
    void deleteFile(String tmpFilename);

    /** Удаление entity файла из БД
     * @param fileInfo entity файла
     */
    void deleteFromDb(FileInfo fileInfo);
}