package ru.kpfu.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.security.models.Comment;
import ru.kpfu.security.models.Post;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.PostRepository;
import ru.kpfu.security.repositories.StudentRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private final PostRepository postRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository,
                              StudentRepository studentRepository) {
        this.postRepository = postRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void commentPost(Long postId,
                            Comment comment,
                            Long studentId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<Student> studentOpt = studentRepository.findById(studentId);

        if (postOpt.isPresent() && studentOpt.isPresent()) {
            Post post = postOpt.get();
            comment.setStudent(studentOpt.get());
            post.getComments().add(comment);
            postRepository.save(post);
        }
    }
}
