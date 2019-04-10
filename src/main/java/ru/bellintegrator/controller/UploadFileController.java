package ru.bellintegrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.service.FileService;

/**
 * Загрузка файлов
 */
@Controller
@RequestMapping("/upload")
public class UploadFileController {
    /**
     * Сервис работы с файлами
     */
    @Autowired
    private FileService fileService;

    /** Отображение страницы с загрузкой файлов + сообщения от других пользователей
     * @param userView dto пользователя
     * @param model передача параметров во фронт
     * @return страница с загрузкой файлов
     */
    @GetMapping
    public String upload(@AuthenticationPrincipal UserView userView, Model model) {
        model.addAttribute("username", userView.getUsername());
        return "multipartfile/upload";
    }

    /** Сохранение файла на сервере
     * @param file загружаемый файл
     * @param userView dto пользователя
     * @param model передача параметров во фронт
     * @return страница с загрузкой файлов
     */
    @PostMapping
    public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file, @AuthenticationPrincipal UserView userView, Model model) {
        model.addAttribute("username", userView.getUsername());
        if (!StringUtils.isEmpty(file.getOriginalFilename())) {
            try {
                fileService.store(file, userView);
                model.addAttribute("message", "File uploaded successfully! -> filename = " + file.getOriginalFilename());
            } catch (Exception e) {
                model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
            }
        } else {
            model.addAttribute("message", "Fail! -> no file selected!");
        }
        return "multipartfile/upload";
    }
}
