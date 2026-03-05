package com.example.MiniBlog;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {

    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    // Get all posts
    public List<PostEntity> getPublishedPosts() {
        return repo.findByPublishedTrueOrderByCreatedAtDesc();
    }
    // Create post
    public PostEntity createPost(PostEntity post) {

        if (post.getLikes() == null) {
            post.setLikes(0);
        }

        return repo.save(post);
    }

    // Delete post
    public void deletePost(Long id) {
        repo.deleteById(id);
    }
    // Get post by ID
    public PostEntity getPostById(Long id) {
        return repo.findById(id).orElse(null);
    }
}