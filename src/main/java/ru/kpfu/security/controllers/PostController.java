package ru.kpfu.security.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.security.models.Comment;
import ru.kpfu.security.models.Post;
import ru.kpfu.security.models.PostDTO;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.PostRepository;
import ru.kpfu.security.services.PostService;
import ru.kpfu.security.utils.PostMapper;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("posts")
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final PostMapper postMapper;

    @Autowired
    public PostController(PostRepository postRepository,
                          PostService postService,
                          PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postService = postService;
        this.postMapper = postMapper;
    }


    @GetMapping
    public String allPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "post/posts";
    }

    @GetMapping("{postId}")
    public String post(@PathVariable Long postId,
                       @ModelAttribute Comment comment,
                       Model model) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            model.addAttribute("post", postOpt.get());
            model.addAttribute("comment", comment);
            model.addAttribute("comments", postOpt.get().getComments());
        }
        return "post/post";
    }

    @GetMapping("{postId}/rate")
    public ResponseEntity<String> ratePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal Student student) {
        postService.ratePost(postId, student.getId());
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            return ResponseEntity.ok(String.valueOf(postOpt.get().getLikes().size()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/{postId}/edit")
    public String editPost(@PathVariable Long postId,
                           @AuthenticationPrincipal Student student,
                           Model model) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            if (!post.getAuthor().equals(student)) {
                return null;
            }
            model.addAttribute("postDTO", postMapper.postToDto(post));
        }
        return "post/post_edit";
    }

    @PatchMapping("/{postId}")
    public String patch(@Valid @ModelAttribute("postDTO") PostDTO postDTO,
                        BindingResult bindingResult,
                        @PathVariable Long postId) {
        if (bindingResult.hasErrors()) {
            return "post/post_edit";
        }
        postService.updatePost(postDTO, postId);
        return "redirect:";
    }

    @GetMapping("/new")
    public String createPostPage(@ModelAttribute PostDTO postDTO) {
        return "post/post_new";
    }

    @PostMapping()
    public String createPost(@ModelAttribute PostDTO postDTO,
                             @AuthenticationPrincipal Student student) {
        postRepository.save(new Post(postDTO.getTitle(), postDTO.getText(), student));
        return "redirect:/posts";
    }

}
