package com.example.socialmedia;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    @Test
    public void createPost() {
        // Given
        User author = new User();
        author.setId(1L);
        author.setUsername("testUser");

        // When
        Post post = new Post();
        post.setTitle("Test Title");
        post.setBody("Test Body");
        post.setAuthor(author);

        // Then
        assertNotNull(post);
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Body", post.getBody());
        assertEquals(author, post.getAuthor());
    }

    @Test
    public void updatePost() {
        // Given
        User author = new User();
        author.setId(1L);
        author.setUsername("testUser");

        Post post = new Post();
        post.setTitle("Original Title");
        post.setBody("Original Body");
        post.setAuthor(author);

        // When
        post.setTitle("Updated Title");
        post.setBody("Updated Body");

        // Then
        assertEquals("Updated Title", post.getTitle());
        assertEquals("Updated Body", post.getBody());
    }
    @Test
    public void testAllArgsConstructor() {
        // Given
        User author = new User();
        author.setId(1L);
        author.setUsername("testUser");

        // When
        Post post = new Post(1L, "Test Title", "Test Body", author, null);

        // Then
        assertNotNull(post);
        assertEquals(1L, post.getId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Body", post.getBody());
        assertEquals(author, post.getAuthor());
        assertNull(post.getCreatedAt());
    }
}

