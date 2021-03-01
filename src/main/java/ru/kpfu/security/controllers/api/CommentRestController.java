package ru.kpfu.security.controllers.api;

import org.springframework.web.bind.annotation.*;
import ru.kpfu.security.repositories.CommentRepository;
import ru.kpfu.security.models.Comment;
import ru.kpfu.security.models.Post;
import ru.kpfu.security.repositories.PostRepository;

import java.util.List;
import java.util.Optional;
//TODO: Refactor API
@RestController
@RequestMapping("/api/v1/")
public class CommentRestController {
    CommentRepository commentRepository;
    PostRepository postRepository;

    CommentRestController(CommentRepository commentRepository,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("posts/{postId}/comments")
    public List<Comment> postComments(@PathVariable Long postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        return postOpt.map(Post::getComments).orElse(null);
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
