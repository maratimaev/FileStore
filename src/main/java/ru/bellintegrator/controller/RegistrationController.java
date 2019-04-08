package ru.bellintegrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.service.UserService;

/**
 * Создание и активация пользователя
 */
@Controller
public class RegistrationController {
    /**
     * Сервис работы с пользователями
     */
    @Autowired
    private UserService userService;

    /** Отображение страницы создания пользователя
     * @return страница регистрации
     */
    @GetMapping("/registration")
    public String registration() {
        return "multipartfile/registration";
    }

    /** Создание пользователя
     * @param userView  dto пользователя
     * @param model передача параметров во фронт
     * @return страница логина в случае успеха, страница регистрации в случае существования пользователя с таким именем
     */
    @PostMapping("/registration")
    public String addUser(@Validated UserView userView, Model model) {
        if (userService.userExist(userView.getUsername())) {
             if(userService.hasActivated(userView)) {
                model.addAttribute("message", "User already exists and activated!");
                 return "multipartfile/registration";
            } else {
                 model.addAttribute("message", "User already exists, but not activated. Email resented.");
                 model.addAttribute("messageType", "danger");
             }
        }
        userService.createUser(userView);
        return "multipartfile/login";
    }

    /** Активация пользователя по коду из письма, проверка что код не просрочен на 1 день
     * @param code код из письма
     * @param model передача параметров во фронт
     * @return страница логина
     */
    @GetMapping("/activate/{code}")
    public String activateEmail(@PathVariable String code, Model model) {
        if(userService.hasCodeExpired(code)) {
            model.addAttribute("activated", "Activation code expired! Create user again.");
            model.addAttribute("activatedType", "danger");
        } else {
            boolean isActivated = userService.activateUser(code);
            if (isActivated) {
                model.addAttribute("activated", "User successfully activated!");
                model.addAttribute("activatedType", "success");
            } else {
                model.addAttribute("notActivated", "Activation code not found!");
                model.addAttribute("activatedType", "danger");
            }
        }
        return "multipartfile/login";
    }
}

