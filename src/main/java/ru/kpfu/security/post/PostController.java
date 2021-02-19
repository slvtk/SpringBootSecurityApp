package ru.kpfu.security.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.security.comment.CommentRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    PostRepository postRepository;
    CommentRepository commentRepository;

    PostController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public Iterable<Post> allPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> post(@PathVariable Long postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            return new ResponseEntity<>(postOpt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
}
