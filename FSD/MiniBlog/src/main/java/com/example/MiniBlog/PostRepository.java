package com.example.MiniBlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByPublishedTrueOrderByCreatedAtDesc();
    List<PostEntity> findByPublishedFalseAndPublishAtBefore(LocalDateTime time);


}
