package ru.kpfu.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.services.FileUploadService;

import java.io.IOException;

@Controller
@RequestMapping("fileUpload")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }


    @GetMapping
    public String fileUpload() {
        return "file_upload";
    }

    @PostMapping
    @ResponseBody
    public String file(MultipartFile file,
                       @AuthenticationPrincipal Student student) throws IOException {
        return fileUploadService.saveFile(file, student.getId()).getPath();
    }
}
