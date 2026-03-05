package com.example.MiniBlog;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String authorUsername;

    private LocalDateTime createdAt;

    @ManyToOne
    private PostEntity post;

    public CommentEntity(){}

    public CommentEntity(String content, String username, PostEntity post){
        this.content = content;
        this.authorUsername = username;
        this.post = post;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PostEntity getPost() {
        return post;
    }
}