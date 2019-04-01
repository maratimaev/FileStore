package ru.bellintegrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bellintegrator.dto.MessageView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.service.GroupService;
import ru.bellintegrator.service.MessageService;
import ru.bellintegrator.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Изменение членства в группах предоставления доступа к файлам
 */
@Controller
@RequestMapping("/changegroup")
public class GroupController {
    /**
     * Сервис работы с пользователем
     */
    @Autowired
    private UserService userService;

    /**
     * Сервис работы с группами
     */
    @Autowired
    private GroupService groupService;

    /**
     * Сервис работы с сообщениями
     */
    @Autowired
    private MessageService messageService;

    /** Отображение членства в группах
     * @param userView dto пользователя
     * @param model передача параметров во фронт
     * @return страница со списком пользователей, которые имеют доступ к файлам userView
     */
    @GetMapping
    public String getMembership(@AuthenticationPrincipal UserView userView, Model model){
        List<String> allMinusUser = userService.getUsernameListMinus(userView);
        Set<String> userListGroup = groupService.getListGroup(userView);
        Set<String> userDownloadGroup = groupService.getDownloadGroup(userView);

        List<MessageView> accessMessagesFromCurrentUser = messageService.getAccessMessagesFrom(userView);
        if(!(accessMessagesFromCurrentUser == null || accessMessagesFromCurrentUser.isEmpty())) {
            model.addAttribute("accessMessagesFromCurrentUser", accessMessagesFromCurrentUser);
        }

        model.addAttribute("users", allMinusUser);
        model.addAttribute("userListGroup", userListGroup);
        model.addAttribute("userDownloadGroup", userDownloadGroup);
        return "multipartfile/changeGroup";
    }

    /** Изменение членства в группах
     * @param userView dto пользователя
     * @param form данные со странцы
     * @return возвращение на странцу загрузки файлов
     */
    @PostMapping
    public String saveMembership(@AuthenticationPrincipal UserView userView, @RequestParam Map<String, String> form){
        messageService.saveMessages(userView, form);
        groupService.saveUserGroups(userView, form);
        return "redirect:/upload";
    }
}
