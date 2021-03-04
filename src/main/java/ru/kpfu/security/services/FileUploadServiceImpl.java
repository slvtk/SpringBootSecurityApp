package ru.kpfu.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.security.models.File;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.FileRepository;
import ru.kpfu.security.repositories.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final FileRepository fileRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public FileUploadServiceImpl(FileRepository fileRepository,
                                 StudentRepository studentRepository) {
        this.fileRepository = fileRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public File saveFile(MultipartFile file,
                         Long studentId) throws IOException {
        String UPLOAD_DIR = "C:\\StudyProjects\\SpringBootSecurityExample\\uploads";
        String encodedFileName = UUID.randomUUID().toString();
        Path path = Paths.get(UPLOAD_DIR, encodedFileName +
                file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
        Files.write(path, file.getBytes());
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            File createdFile = new File(file.getOriginalFilename(), "/uploads/" + path.getFileName().toString(), studentOpt.get());
            return fileRepository.save(createdFile);
        } else {
            throw new IOException();
        }
    }
}
