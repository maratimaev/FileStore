package ru.bellintegrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bellintegrator.dto.FileInfoView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.service.FileService;

import java.util.ArrayList;
import java.util.List;

/**
 * Контрллер отображения, скачивания и удаления файлов пользователя
 */
@Controller
@RequestMapping("/files")
public class DownloadFileController {

    /**
     * Сервис работы с файлами
     */
    @Autowired
    private FileService fileService;

    /** Получение списка своих и доступных "чужих" файлов
     * @param userView  dto пользователя
     * @param model передача параметров во фронт
     * @return страница со списком файлов
     */
    @GetMapping
    public String getListFiles(@AuthenticationPrincipal UserView userView, Model model) {
        List<FileInfoView> userFiles = fileService.addDownloadlink(fileService.getUserFiles(userView.getUsername()));
        List<FileInfoView> listableSharedFiles = fileService.getListableSharedFiles(userView.getUsername());
        List<FileInfoView> downloadableSharedFiles = fileService.addDownloadlink(fileService.getDownloadableSharedFiles(userView.getUsername()));
        List<FileInfoView> sharedFiles = new ArrayList<>();
        sharedFiles.addAll(downloadableSharedFiles);
        sharedFiles.addAll(listableSharedFiles);

        model.addAttribute("userfiles", userFiles);
        model.addAttribute("sharedfiles", sharedFiles);
        return "multipartfile/listFiles";
    }

    /** Скачивание файла
     * @param tmpFilename имя файла с UUID
     * @return ссылка на скачивание файла
     */
    @GetMapping("/{tmpFilename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String tmpFilename) {
        FileInfoView dbFile = fileService.findFile(tmpFilename);
        Resource storedFile = fileService.downloadFile(tmpFilename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFilename() + "\"")
                .body(storedFile);
    }

    /** Удаление файла
     * @param tmpFilename имя файла с UUID
     * @return страница со списком файлов
     */
    @PostMapping
    public String deleteFile(@RequestParam String tmpFilename){
        fileService.deleteFile(tmpFilename);
        return "redirect:/files";
    }

}
