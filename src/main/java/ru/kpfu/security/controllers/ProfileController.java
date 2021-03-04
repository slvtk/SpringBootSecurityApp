package ru.kpfu.security.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.security.models.Student;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping
    public String profile(@AuthenticationPrincipal Student student,
                          Model model) {
        model.addAttribute("student", student);
        return "student/profile";
    }
}
