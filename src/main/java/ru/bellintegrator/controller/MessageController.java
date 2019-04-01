package ru.bellintegrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.service.GroupService;
import ru.bellintegrator.service.MessageService;

/**
 * Работа с сообщениями между пользователями
 */
@Controller
public class MessageController {

    /**
     * Сервис работы с группми
     */
    @Autowired
    private GroupService groupService;

    /**
     * Сервис работы с сообщениями
     */
    @Autowired
    private MessageService messageService;

    /** Обработка нажатия кнопки "Согласен" на запрос доступа к файлам userView
     * @param userView dto пользователя
     * @param msgUuid UUID сообщения
     * @return возвращение на странцу загрузки файлов
     */
    @PostMapping("/acceptMsg")
    public String acceptAccess(@AuthenticationPrincipal UserView userView, @RequestParam String msgUuid) {
        groupService.saveUserGroupByMessage(userView, msgUuid);
        return "redirect:/upload";
    }

    /** Обработка нажатия кнопки "Отказ" на запрос доступа к файлам пользователя
     * @param msgUuid UUID сообщения
     * @return возвращение на странцу загрузки файлов
     */
    @PostMapping("/declineMsg")
    public String declineAccess(@RequestParam String msgUuid) {
        messageService.declineAccess(msgUuid);
        return "redirect:/upload";
    }

    /** Обработка нажатия кнопки "Ok" на уведомление о предоставлении доступа к файлам
     * @param msgUuid UUID сообщения
     * @return возвращение на странцу загрузки файлов
     */
    @PostMapping("/okMsg")
    public String getOkInfoMessage(@RequestParam String msgUuid) {
        messageService.deleteMessage(msgUuid);
        return "redirect:/upload";
    }

}
