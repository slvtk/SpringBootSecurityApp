package ru.kpfu.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.StudentRepository;

import java.util.Optional;

@Controller
@RequestMapping("students")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String studentsList(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "student/students";
    }

    @GetMapping("/{studentId}")
    public String studentPage(@PathVariable Long studentId,
                              Model model) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            model.addAttribute("student", student);
        }
        return "student/student_page";
    }

}
