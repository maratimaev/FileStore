package ru.bellintegrator.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bellintegrator.dto.UserView;

/**
 * Страница приветствия, доступна всем
 */
@Controller
public class WellcomeController {

    /** Страница приветствия
     * @return wellcome page
     */
    @GetMapping("/")
    public String wellcome(@AuthenticationPrincipal UserView userView, Model model) {
        if (userView != null) {
            model.addAttribute("username", userView.getUsername());
        }
        return "multipartfile/wellcome";
    }
}
