package ru.kpfu.security.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.StudentRepository;

@RestController
@RequestMapping("api/v1/students")
public class StudentRestController {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentRestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("{studentId}")
    public Student getStudent(@PathVariable("studentId") Long studentId) {
        return studentRepository.findById(studentId).get();
    }
}
