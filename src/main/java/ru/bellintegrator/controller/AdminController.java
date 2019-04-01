package ru.bellintegrator.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bellintegrator.dto.FileInfoView;
import ru.bellintegrator.service.FileService;

import java.util.List;

/**
 * Работа с файлами под ролью ADMIN
 */
@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    /**
     *  Сервис работы с файлами
     */
    @Autowired
    private FileService fileService;

    /** Отображение всех файлов пользователей
     * @param model передача параметров во фронт
     * @return страница со списком файлов
     */
    @GetMapping
    public String getAllFiles(Model model){
        List<FileInfoView> allFiles = fileService.addDownloadlink(fileService.getAllFiles());
        model.addAttribute("allfiles", allFiles);
        return "multipartfile/adminFiles";
    }

    /** Удаление файла
     * @param tmpFilename UUID имя файла
     * @return страница со списком файлов
     */
    @PostMapping
    public String deleteFile(@RequestParam String tmpFilename){
        fileService.deleteFile(tmpFilename);
        return "redirect:/admin";
    }
}
