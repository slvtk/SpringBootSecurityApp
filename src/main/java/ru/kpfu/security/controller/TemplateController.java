package ru.kpfu.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.security.student.Student;
import ru.kpfu.security.student.StudentRepository;

import java.util.List;

@Controller
public class TemplateController {

    StudentRepository studentRepository;

    @Autowired
    public TemplateController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
}
