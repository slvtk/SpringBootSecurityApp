package ru.kpfu.security.controllers.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.models.Post;
import ru.kpfu.security.repositories.PostRepository;
import ru.kpfu.security.services.PostService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostRepository postRepository;
    private final PostService postService;

    @Autowired
    PostRestController(PostRepository postRepository,
                       PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @GetMapping
    public Iterable<Post> allPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> post(@PathVariable Long postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        return postOpt.map(post -> new ResponseEntity<>(post, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PatchMapping("/{postId}")
    public Post editPost(@RequestBody Post editedPost,
                         @PathVariable Long postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            if (editedPost.getTitle() != null) {
                post.setTitle(editedPost.getTitle());
            }
            if (editedPost.getText() != null) {
                post.setText(editedPost.getText());
            }
            return postRepository.save(post);
        }
        return null;
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postRepository.deleteById(postId);
    }

    @GetMapping("/{postId}/rate")
    public void likePost(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = (Student)authentication.getPrincipal();
        postService.ratePost(postId, student.getId());
    }
}
