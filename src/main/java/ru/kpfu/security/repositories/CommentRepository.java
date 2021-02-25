package ru.kpfu.security.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.security.models.Comment;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
}