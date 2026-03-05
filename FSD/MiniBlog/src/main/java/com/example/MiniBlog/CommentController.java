package com.example.MiniBlog;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;

    public CommentController(CommentRepository commentRepo, PostRepository postRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }

    @PostMapping("/{postId}")
    public CommentEntity addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest req) {

        PostEntity post = postRepo.findById(postId).orElseThrow();

        CommentEntity comment = new CommentEntity(req.content, req.username, post);
        return commentRepo.save(comment);
    }

    @GetMapping("/{postId}")
    public List<CommentEntity> getComments(@PathVariable Long postId) {
        return commentRepo.findByPostId(postId);
    }
}