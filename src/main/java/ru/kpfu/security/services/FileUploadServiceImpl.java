package ru.kpfu.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.security.models.File;
import ru.kpfu.security.models.FileType;
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
                         Long studentId,
                         FileType fileType) throws IOException {

        String UPLOAD_DIR = "C:\\StudyProjects\\SpringBootSecurityExample\\uploads";
        if (fileType.equals(FileType.AVATAR)) {
            UPLOAD_DIR = "C:\\StudyProjects\\SpringBootSecurityExample\\uploads\\avatars";
        }
        String encodedFileName = UUID.randomUUID().toString();
        log.info(file.toString());
        Path path = Paths.get(UPLOAD_DIR, encodedFileName +
                file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
        Files.write(path, file.getBytes());
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (student.getFiles().stream().anyMatch(t -> t.getFileType().equals(FileType.AVATAR))) {
                Optional<File> avatarOpt = fileRepository.findAvatarByStudentId(student.getId());
                if (avatarOpt.isPresent()) {
                    File avatar = avatarOpt.get();
                    avatar.setName(file.getOriginalFilename());
                    avatar.setPath("/uploads/avatars/" + path.getFileName().toString());
                    return fileRepository.save(avatar);
                }
            } else {
                File createdFile = new File(file.getOriginalFilename(), "/uploads/avatars/" + path.getFileName().toString(), student, fileType);
                return fileRepository.save(createdFile);
            }
        } else {
            throw new IOException();
        }
        return null;
    }
}
