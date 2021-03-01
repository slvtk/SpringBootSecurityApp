package ru.kpfu.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.security.models.Comment;
import ru.kpfu.security.models.Student;
import ru.kpfu.security.repositories.CommentRepository;
import ru.kpfu.security.services.CommentService;

@Controller
@RequestMapping("posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentService commentService,
                             CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    @PostMapping
    public String comment(@PathVariable Long postId,
                          @ModelAttribute Comment comment,
                          @AuthenticationPrincipal Student student) {
        commentService.commentPost(postId, comment, student.getId());
        return "redirect:/posts/" + postId;
    }

    @DeleteMapping("{commentId}")
    public String deleteComment(@PathVariable Long commentId,
                                @PathVariable Long postId) {
        commentRepository.deleteById(commentId);
        return "redirect:/posts/" + postId;
    }
}
