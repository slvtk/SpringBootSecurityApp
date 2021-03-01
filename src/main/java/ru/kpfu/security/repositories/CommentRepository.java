package ru.kpfu.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.security.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}