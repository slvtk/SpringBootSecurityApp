package ru.kpfu.security.services;

import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.security.models.File;
import ru.kpfu.security.models.FileType;


import java.io.IOException;

public interface FileUploadService {
    File saveFile(MultipartFile file, Long studentId, FileType fileType) throws IOException;
}
