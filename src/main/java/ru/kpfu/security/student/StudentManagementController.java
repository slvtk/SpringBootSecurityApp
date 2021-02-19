package ru.kpfu.security.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {
    StudentRepository studentRepository;

    @Autowired
    public StudentManagementController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ADMIN_TRAINEE')")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('students:write')")
    public Student registerStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @DeleteMapping("{studentId}")
    @PreAuthorize("hasAuthority('students:write')")
    public void deleteStudent(@PathVariable Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @PutMapping("{studentId}")
    @PreAuthorize("hasAuthority('students:write')")
    public Student updateStudent(@RequestBody Student editedStudent,
                                 @PathVariable Long studentId) {
        Student student = studentRepository.findById(studentId).get();
        student.setName(editedStudent.getName());
        return studentRepository.save(student);
    }
}
