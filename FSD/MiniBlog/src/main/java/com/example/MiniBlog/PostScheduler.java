package com.example.MiniBlog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

    @Component
    public class PostScheduler {

        private final PostRepository repo;

        public PostScheduler(PostRepository repo){
            this.repo = repo;
        }

        @Scheduled(fixedRate = 60000) // every 1 minute
        public void publishScheduledPosts(){

            List<PostEntity> posts = repo.findByPublishedFalseAndPublishAtBefore(LocalDateTime.now());

            for(PostEntity post : posts){
                post.setPublished(true);
                repo.save(post);
            }

        }
    }

