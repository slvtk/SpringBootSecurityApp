package ru.kpfu.security.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class File {

    public File(String name, String path, Student student, FileType fileType) {
        this.name = name;
        this.path = path;
        this.student = student;
        this.fileType = fileType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    @Enumerated(EnumType.STRING)
    private FileType fileType;
    @ManyToOne
    private Student student;
}
