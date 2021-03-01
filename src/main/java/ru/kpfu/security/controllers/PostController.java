package ru.kpfu.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String ratePost(@PathVariable Long postId,
                           @AuthenticationPrincipal Student student) {
        postService.ratePost(postId, student.getId());
        return "redirect:";
    }

    @GetMapping("/{postId}/edit")
    public String editPost(@PathVariable Long postId,
                           Model model) {
        Optional<Post> postOpt = postRepository.findById(postId);
        postOpt.ifPresent(post -> model.addAttribute("postDTO", postMapper.postToDto(post)));
        return "post/post_edit";
    }


    //TODO: разобраться почему не работает валидация
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
}
