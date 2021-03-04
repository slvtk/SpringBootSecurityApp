package ru.kpfu.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.security.models.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
