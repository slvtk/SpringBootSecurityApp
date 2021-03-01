package ru.kpfu.security.services;

import ru.kpfu.security.models.PostDTO;

public interface PostService {

    void ratePost(Long postId, Long studentId);

    void updatePost(PostDTO postDTO, Long postId);

}
