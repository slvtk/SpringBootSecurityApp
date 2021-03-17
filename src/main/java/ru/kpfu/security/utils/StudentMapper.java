package ru.kpfu.security.utils;

import ru.kpfu.security.models.Student;
import ru.kpfu.security.models.StudentDTO;

public interface StudentMapper {

    Student studentFromDto(StudentDTO studentDto);

    StudentDTO studentToDto(Student student);
}
