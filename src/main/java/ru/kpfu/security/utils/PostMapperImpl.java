package ru.kpfu.security.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.security.models.Post;
import ru.kpfu.security.models.PostDTO;
import ru.kpfu.security.repositories.PostRepository;

import java.util.Optional;

@Component
@Data
public class PostMapperImpl implements PostMapper {

    private final PostRepository postRepository;

    @Autowired
    PostMapperImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post postFromDto(PostDTO postDTO) {
        Optional<Post> postOpt = postRepository.findById(postDTO.getId());
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            post.setTitle(postDTO.getTitle());
            post.setText(postDTO.getText());
            return post;
        }
        return null;
    }

    @Override
    public PostDTO postToDto(Post post) {
        return new PostDTO(post.getId(),
                post.getTitle(),
                post.getText());
    }
}
