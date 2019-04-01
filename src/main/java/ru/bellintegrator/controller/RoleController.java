package ru.bellintegrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bellintegrator.entity.Role;
import ru.bellintegrator.service.UserService;

import java.util.Map;

/**
 * Изменение ролей пользователей
 */
@Controller
@RequestMapping("/manageUsers")
//@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {

    /**
     * Сервис работы с пользователями
     */
    @Autowired
    private UserService userService;

    /** Отображение списка пользователей
     * @param model передача параметров во фронт
     * @return страница со списком пользователей
     */
    @RequestMapping
    public String userList(Model model){
        model.addAttribute("users", userService.getUserViewList());
        return "multipartfile/userList";
    }

    /** Изменение ролей пользователей
     * @param userId id пользователя
     * @param model передача параметров во фронт
     * @return страница с ролями пользователя
     */
    @GetMapping("{userId}")
    public String userEditForm(@PathVariable Long userId, Model model){
        model.addAttribute("user", userService.getUserView(userId));
        model.addAttribute("roles", Role.values());
        return "multipartfile/userEdit";
    }

    /** Сохранение имени и ролей пользователя
     * @param username измененное имя пользователя
     * @param form данные со странцы
     * @param userId id пользователя
     * @return страница со списком пользователей
     */
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") Long userId,
            Model model
    ) {
        if (userService.userExist(username)) {
            model.addAttribute("message", "Username already exists!");
            model.addAttribute("user", userService.getUserView(userId));
            model.addAttribute("roles", Role.values());
            return "multipartfile/userEdit";
        }
        userService.save(username, form, userId);
        return "redirect:/manageUsers";
    }
    }

