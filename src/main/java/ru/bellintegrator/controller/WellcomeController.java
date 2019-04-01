package ru.bellintegrator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Страница приветствия, доступна всем
 */
@Controller
public class WellcomeController {

    /** Страница приветствия
     * @return wellcome page
     */
    @GetMapping("/")
    public String wellcome() {
        return "multipartfile/wellcome";
    }
}
