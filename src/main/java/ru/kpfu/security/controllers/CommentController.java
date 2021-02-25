package ru.kpfu.security.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.security.repositories.CommentRepository;
import ru.kpfu.security.models.Comment;
import ru.kpfu.security.models.Post;
import ru.kpfu.security.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {
    CommentRepository commentRepository;
    PostRepository postRepository;

    CommentController(CommentRepository commentRepository,
                      PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("posts/{postId}/comments")
    public List<Comment> postComments(@PathVariable Long postId,
                                      @PageableDefault Pageable pageable) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()){
            return postOpt.get().getComments();
        }
        return null;
    }

    @PostMapping("posts/{postId}/comments")
    public Post createComment(@PathVariable Long postId,
                              @RequestBody Comment comment) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            post.getComments().add(commentRepository.save(comment));
            commentRepository.save(comment);
            return postRepository.save(post);
        }
        return null;
    }
}
