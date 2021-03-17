package ru.kpfu.security.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.security.models.File;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.models.StudentDTO;
import ru.kpfu.security.repositories.FileRepository;
import ru.kpfu.security.repositories.StudentRepository;
import ru.kpfu.security.utils.StudentMapper;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final FileRepository fileRepository;

    @Autowired
    public ProfileController(StudentRepository studentRepository,
                             StudentMapper studentMapper,
                             FileRepository fileRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.fileRepository = fileRepository;
    }

    @GetMapping
    public String profile(@AuthenticationPrincipal Student studentSec,
                          Model model) {
        Optional<Student> studentOpt = studentRepository.findById(studentSec.getId());
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            model.addAttribute("student", student);
            Optional<File> avatarOpt = fileRepository.findAvatarByStudentId(student.getId());
            if (avatarOpt.isPresent()) {
                model.addAttribute("avatar", avatarOpt.get());
            }
        }
        return "student/profile";
    }

    @GetMapping("/edit")
    public String editProfile(
            @AuthenticationPrincipal Student student,
            Model model
    ) {
        Optional<Student> studentOpt = studentRepository.findById(student.getId());
        if (studentOpt.isPresent()) {
            model.addAttribute("studentDto", studentMapper.studentToDto(studentOpt.get()));
            return "/student/profile_edit";
        }
        return null;
    }

    @PatchMapping
    public String patchStudent(@ModelAttribute StudentDTO studentDTO) {
        studentRepository.save(studentMapper.studentFromDto(studentDTO));
        return "redirect:/profile";
    }
}
