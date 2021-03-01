package ru.kpfu.security.services;

import ru.kpfu.security.models.Comment;

public interface CommentService {
    void commentPost(Long postId, Comment comment, Long studentId);
}
