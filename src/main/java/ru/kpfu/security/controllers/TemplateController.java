package ru.kpfu.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.security.models.RegistrationForm;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.StudentRepository;
import ru.kpfu.security.services.MailSender;
import ru.kpfu.security.services.RegistrationService;

import java.util.List;

@Controller
public class TemplateController {

    private final StudentRepository studentRepository;
    private final RegistrationService registrationService;

    @Autowired
    public TemplateController(StudentRepository studentRepository,
                              RegistrationService registrationService,
                              MailSender mailSender) {
        this.studentRepository = studentRepository;
        this.registrationService = registrationService;
    }

    @GetMapping("login")
    public String getLoginTemplate() {
        return "login";
    }

    @GetMapping("students")
    public String getAllPostsTemplate(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students";
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

    @GetMapping("profile")
    public String profile(Model model) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentUser", student);
        return "profile";
    }

    @GetMapping("confirm")
    public String confirmation(@RequestParam String token) {
        registrationService.confirmToken(token);
        return "redirect:/login";
    }
}
