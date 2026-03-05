package com.example.MiniBlog;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService service;
    private final UserRepository userRepo;

    @Autowired
    private PostRepository postRepository;

    public PostController(PostService service, UserRepository userRepo) {
        this.service = service;
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<PostEntity> getAll() {
        return service.getPublishedPosts();
    }

    @PostMapping
    public PostEntity createPost(
            @RequestParam String email,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(required = false) String publishAt,
            @RequestParam(required = false) MultipartFile image
    ) throws Exception {

        User user = userRepo.findByEmail(email);

        PostEntity post = new PostEntity();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);

        if (publishAt != null) {
            post.setPublishAt(LocalDateTime.parse(publishAt));
            post.setPublished(false);
        } else {
            post.setPublished(true);
        }

        if (image != null && !image.isEmpty()) {

            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            Path path = Paths.get(System.getProperty("user.dir"), "uploads", fileName);

            Files.createDirectories(path.getParent());

            Files.write(path, image.getBytes());

            post.setImageUrl("/uploads/" + fileName);
        }

        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public PostEntity updatePost(@PathVariable Long id, @RequestBody PostEntity post) {

        PostEntity existing = postRepository.findById(id).orElseThrow();

        existing.setTitle(post.getTitle());
        existing.setContent(post.getContent());

        return postRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, @RequestParam String email) {

        PostEntity post = service.getPostById(id);

        if (post == null) {
            return "Post not found";
        }

        if (post.getUser() == null) {
            return "Post has no owner";
        }

        if (!post.getUser().getEmail().equals(email)) {
            return "You can only delete your own posts";
        }

        service.deletePost(id);

        return "Post deleted";
    }

    @PutMapping("/{id}/like")
    public PostEntity likePost(@PathVariable Long id) {

        PostEntity post = service.getPostById(id);

        post.setLikes(post.getLikes() + 1);

        return postRepository.save(post);
    }
}