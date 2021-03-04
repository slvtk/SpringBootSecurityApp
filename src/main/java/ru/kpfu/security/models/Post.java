package ru.kpfu.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    public Post(Long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public Post(String title, String text, Student author) {
        this.title = title;
        this.text = text;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 512)
    private String text;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    List<Comment> comments;
    @ManyToOne
    private Student author;
    @ManyToMany
    @JoinTable(name = "post_like_students",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> likes;
}
