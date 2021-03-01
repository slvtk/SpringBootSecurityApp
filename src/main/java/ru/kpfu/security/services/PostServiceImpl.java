package ru.kpfu.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.security.models.Post;
import ru.kpfu.security.models.PostDTO;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.PostRepository;
import ru.kpfu.security.repositories.StudentRepository;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           StudentRepository studentRepository) {
        this.postRepository = postRepository;
        this.studentRepository = studentRepository;
    }

    //TODO: Разобраться с JoinTable, mappedBy ManyToMany
    @Override
    public void ratePost(Long postId, Long userId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<Student> studentOpt = studentRepository.findById(userId);
        if (postOpt.isPresent() && studentOpt.isPresent()) {
            Post post = postOpt.get();
            Student student = studentOpt.get();
            if (!post.getLikes().contains(student)) {
                post.getLikes().add(student);
            } else {
                post.getLikes().remove(student);
            }
            postRepository.save(post);
        }
    }

    @Override
    public void updatePost(PostDTO postDTO, Long postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            post.setTitle(postDTO.getTitle());
            post.setText(postDTO.getText());
            postRepository.save(post);
        }
    }
}
