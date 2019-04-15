package ru.bellintegrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bellintegrator.dto.Analytics;
import ru.bellintegrator.dto.FileInfoView;
import ru.bellintegrator.service.AnalyticsService;
import ru.bellintegrator.service.FileService;

import java.util.List;

/**
 * Отображение статистики под ролью ANALYST
 */
@Controller
@RequestMapping("/statistics")
@PreAuthorize("hasAuthority('ANALYST')")
public class AnalystController {

    /**
     * Сервис работы с файлами
     */
    @Autowired
    private FileService fileService;

    /**
     * Сервис работы со статистикой
     */
    @Autowired
    private AnalyticsService analyticsService;

    /** Отображение статистических данных
     * @param model передача параметров во фронт
     * @return страница со статистикой
     */
    @GetMapping
    public String getStatistics(Model model){
        List<Analytics> usersStatistics = analyticsService.getAllUserStat();
        List<FileInfoView> allFiles = fileService.getAllFiles();

        model.addAttribute("usercount", usersStatistics.size());
        model.addAttribute("statistics", usersStatistics);
        model.addAttribute("allfiles", allFiles);
        return "multipartfile/statistics";
    }
}
