package ru.kpfu.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.security.models.File;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value = "SELECT * FROM file WHERE file_type = 'AVATAR' AND student_id = :id ", nativeQuery = true)
    Optional<File> findAvatarByStudentId(@Param("id")Long studentId);

}
