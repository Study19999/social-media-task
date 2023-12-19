package com.example.socialmedia;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    void testCreatePost() throws Exception {
        User author = new User();
        author.setId(1L);
        author.setUsername("testUser");

        Post post = new Post();
        post.setTitle("Test Title");
        post.setBody("Test Body");
        post.setAuthor(author);

        Mockito.when(postService.createPost(Mockito.any(Post.class))).thenReturn(post);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.body").value("Test Body"));
    }


    @Test
    void testGetPostByIdFound() throws Exception {
        Long postId = 1L;
        User author = new User();
        author.setId(1L);
        author.setUsername("testUser");

        Post post = new Post();
        post.setId(postId);
        post.setTitle("Test Title");
        post.setBody("Test Body");
        post.setAuthor(author);

        Mockito.when(postService.getPostById(postId)).thenReturn(Optional.of(post));

        mockMvc.perform(get("/api/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.body").value("Test Body"));
    }

    @Test
    void testGetPostByIdNotFound() throws Exception {
        Long postId = 1L;

        Mockito.when(postService.getPostById(postId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetPostsByAuthor() throws Exception {
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

        Mockito.when(postService.getPostsByAuthorId(authorId)).thenReturn(posts);

        mockMvc.perform(get("/api/posts/by-author/{authorId}", authorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Post 1"))
                .andExpect(jsonPath("$[1].title").value("Post 2"));
    }
    @Test
    void testGetAllPosts() throws Exception {
        User author = new User();
        author.setId(1L);
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

        Mockito.when(postService.getAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/api/posts/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Post 1"))
                .andExpect(jsonPath("$[1].title").value("Post 2"));
    }

    @Test
    void testDeletePost() throws Exception {
        Long postId = 1L;

        mockMvc.perform(delete("/api/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify that the deletePost method was called with the correct postId
        Mockito.verify(postService, Mockito.times(1)).deletePost(postId);
    }
}
