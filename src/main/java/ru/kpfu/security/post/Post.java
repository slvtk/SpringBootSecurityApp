package ru.kpfu.security.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.security.comment.Comment;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    public Post(String title, String text) {
        this.title = title;
        this.text = text;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    @OneToMany
    @JoinColumn(name = "post_id")
    List<Comment> comments;
}
