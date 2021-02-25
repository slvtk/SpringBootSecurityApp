package ru.kpfu.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {
    StudentRepository studentRepository;

    @Autowired
    public StudentManagementController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Student registerStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{studentId}")
    public Student updateStudent(@RequestBody Student editedStudent,
                                 @PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId).get();
        student.setName(editedStudent.getName());
        return studentRepository.save(student);
    }
}
