package ru.kpfu.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.security.models.RegistrationForm;
import ru.kpfu.security.services.RegistrationService;

@Controller
public class TemplateController {

    private final RegistrationService registrationService;

    @Autowired
    public TemplateController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("login")
    public String getLoginTemplate() {
        return "login";
    }

    @GetMapping("registration")
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping("registration")
    public String userRegistration(@ModelAttribute RegistrationForm form) {
        registrationService.register(form);
        return "redirect:/";
    }

    @GetMapping("confirm")
    public String confirmation(@RequestParam String token) {
        registrationService.confirmToken(token);
        return "redirect:/login";
    }
}
