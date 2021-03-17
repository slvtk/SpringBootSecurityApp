package ru.kpfu.security.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.security.models.FileType;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.services.FileUploadService;

import java.io.IOException;

@Slf4j
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
        return fileUploadService.saveFile(file, student.getId(), FileType.DOCUMENT).getPath();
    }

    @PostMapping("/avatar")
    @ResponseBody
    public String uploadAvatar(@ModelAttribute(name = "avatar") MultipartFile avatar,
                               @AuthenticationPrincipal Student student) throws IOException {
        log.info(avatar.toString());
        return fileUploadService.saveFile(avatar, student.getId(), FileType.AVATAR).getPath();
    }
}
