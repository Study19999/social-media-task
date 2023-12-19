package com.example.socialmedia;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getPostsByAuthorId(Long authorId);
    Post createPost(Post post);
    Optional<Post> getPostById(Long id);
    List<Post> getAllPosts();
    void deletePost(Long postId);
}
