package ru.kpfu.security.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.models.StudentDTO;
import ru.kpfu.security.repositories.StudentRepository;

import java.util.Optional;

@Component
@Data
public class StudentMapperImpl implements StudentMapper {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentMapperImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student studentFromDto(StudentDTO studentDto) {
        Optional<Student> studentOpt = studentRepository.findById(studentDto.getId());
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setName(studentDto.getName());
            student.setCity(studentDto.getCity());
            student.setAbout(studentDto.getAbout());
            return student;
        }
        return null;
    }

    @Override
    public StudentDTO studentToDto(Student student) {
        return new StudentDTO(student.getId(),
                student.getName(),
                student.getCity(),
                student.getAbout());
    }
}
