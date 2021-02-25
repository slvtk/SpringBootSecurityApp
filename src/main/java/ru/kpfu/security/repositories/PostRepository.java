package ru.kpfu.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.security.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
