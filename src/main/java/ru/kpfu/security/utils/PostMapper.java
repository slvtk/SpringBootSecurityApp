package ru.kpfu.security.utils;

import ru.kpfu.security.models.Post;
import ru.kpfu.security.models.PostDTO;

public interface PostMapper {

    Post postFromDto(PostDTO postDTO);

    PostDTO postToDto(Post post);

}
