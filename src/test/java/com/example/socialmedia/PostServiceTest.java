package com.example.socialmedia;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Test
    void testCreatePost() {
        User author = new User();
        author.setId(1L);
        author.setUsername("testUser");

        Post post = new Post();
        post.setTitle("Test Title");
        post.setBody("Test Body");
        post.setAuthor(author);

        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost(post);

        assertNotNull(createdPost);
        assertEquals("Test Title", createdPost.getTitle());
        assertEquals("Test Body", createdPost.getBody());
    }

    @Test
    void testGetPostById() {
        Long postId = 1L;
        User author = new User();
        author.setId(1L);
        author.setUsername("testUser");

        Post post = new Post();
        post.setId(postId);
        post.setTitle("Test Title");
        post.setBody("Test Body");
        post.setAuthor(author);

        Mockito.when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Optional<Post> foundPost = postService.getPostById(postId);

        assertTrue(foundPost.isPresent());
        assertEquals("Test Title", foundPost.get().getTitle());
        assertEquals("Test Body", foundPost.get().getBody());
    }
    @Test
    void testGetAllPosts() {
        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Post 1");
        post1.setBody("Body of Post 1");

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Post 2");
        post2.setBody("Body of Post 2");

        List<Post> posts = Arrays.asList(post1, post2);

        Mockito.when(postRepository.findAll()).thenReturn(posts);

        List<Post> allPosts = postService.getAllPosts();

        assertEquals(2, allPosts.size());
        assertEquals("Post 1", allPosts.get(0).getTitle());
        assertEquals("Post 2", allPosts.get(1).getTitle());
    }

    @Test
    void testDeletePost() {
        Long postId = 1L;

        // No need to mock the return value for void methods
        postService.deletePost(postId);

        // Verify that the deleteById method was called with the correct postId
        Mockito.verify(postRepository, Mockito.times(1)).deleteById(postId);
    }


    @Test
    void testGetPostsByAuthorId() {
        Long authorId = 1L;

        User author = new User();
        author.setId(authorId);
        author.setUsername("testUser");

        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Post 1");
        post1.setBody("Body of Post 1");
        post1.setAuthor(author);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Post 2");
        post2.setBody("Body of Post 2");
        post2.setAuthor(author);

        List<Post> posts = Arrays.asList(post1, post2);

        Mockito.when(postRepository.findByAuthorIdOrderByCreatedAtDesc(authorId)).thenReturn(posts);

        List<Post> foundPosts = postService.getPostsByAuthorId(authorId);

        assertEquals(2, foundPosts.size());
        assertEquals("Post 1", foundPosts.get(0).getTitle());
        assertEquals("Post 2", foundPosts.get(1).getTitle());
    }
}
