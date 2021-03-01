package ru.kpfu.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.security.models.ConfirmationToken;
import ru.kpfu.security.models.RegistrationForm;
import ru.kpfu.security.models.Role;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.StudentRepository;

import java.util.Optional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final MailSender mailSender;

    @Autowired
    public UserDetailsServiceImpl(StudentRepository studentRepository,
                                  PasswordEncoder passwordEncoder,
                                  ConfirmationTokenService confirmationTokenService, MailSender mailSender) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.mailSender = mailSender;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Student> studentOpt = studentRepository.findStudentByEmail(email);
        if (studentOpt.isPresent()) {
            return studentOpt.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Async
    public void signUp(RegistrationForm form) {
        Optional<Student> studentOpt = studentRepository.findStudentByEmail(form.getEmail());
        String enableLink = "http://localhost:80/confirm?token=";
        if (studentOpt.isPresent()) {
            ConfirmationToken confirmationToken = confirmationTokenService.createToken(studentOpt.get());
            confirmationTokenService.saveToken(confirmationToken);
            mailSender.send(studentOpt.get().getEmail(), enableLink + confirmationToken.getToken());
        } else {
            Student student = new Student(
                    form.getEmail(),
                    passwordEncoder.encode(form.getPassword()),
                    form.getName(),
                    form.getCity(),
                    form.getAbout(),
                    Role.ROLE_STUDENT
            );

            ConfirmationToken confirmationToken = confirmationTokenService.createToken(student);

            studentRepository.save(student);
            confirmationTokenService.saveToken(confirmationToken);
            mailSender.send(student.getEmail(), enableLink + confirmationToken.getToken());
        }
    }

    public void enableStudent(Student student) {
        Optional<Student> studentOpt = studentRepository.findStudentByEmail(student.getEmail());
        studentOpt.ifPresent(value -> value.setEnabled(true));
    }
}
