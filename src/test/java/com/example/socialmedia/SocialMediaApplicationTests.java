package com.example.socialmedia;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SocialMediaApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {
	}


	@Test
	void testCreatePostEndToEnd() {
		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		Post post = new Post();
		post.setTitle("E2E Test Title");
		post.setBody("E2E Test Body");
		post.setAuthor(user);

		ResponseEntity<Post> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/api/posts",
				post,
				Post.class
		);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("E2E Test Title", response.getBody().getTitle());
		assertEquals("E2E Test Body", response.getBody().getBody());
	}

	@Test
	void testGetAllPostsEndToEnd() {
		ResponseEntity<List<Post>> response = restTemplate.exchange(
				"http://localhost:" + port + "/api/posts/all",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Post>>() {
				}
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		// Add assertions based on your actual implementation and data.
	}

	@Test
	void testDeletePostEndToEnd() {
		// First, create a post to ensure there's something to delete
		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		Post post = new Post();
		post.setTitle("Delete Test Title");
		post.setBody("Delete Test Body");
		post.setAuthor(user);

		ResponseEntity<Post> createResponse = restTemplate.postForEntity(
				"http://localhost:" + port + "/api/posts",
				post,
				Post.class
		);

		assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
		assertNotNull(createResponse.getBody());
		Long postIdToDelete = createResponse.getBody().getId();

		// Now, delete the created post
		ResponseEntity<Void> deleteResponse = restTemplate.exchange(
				"http://localhost:" + port + "/api/posts/" + postIdToDelete,
				HttpMethod.DELETE,
				null,
				Void.class
		);

		assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

		// Optionally, verify that the post has been deleted by trying to retrieve it again
		ResponseEntity<Post> getResponse = restTemplate.getForEntity(
				"http://localhost:" + port + "/api/posts/" + postIdToDelete,
				Post.class
		);

		assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
	}
	@Test
	void testGetPostByIdEndToEnd() {
		// Create a post to ensure there's something to retrieve
		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		Post post = new Post();
		post.setTitle("GetById Test Title");
		post.setBody("GetById Test Body");
		post.setAuthor(user);

		ResponseEntity<Post> createResponse = restTemplate.postForEntity(
				"http://localhost:" + port + "/api/posts",
				post,
				Post.class
		);

		assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
		assertNotNull(createResponse.getBody());
		Long postIdToRetrieve = createResponse.getBody().getId();

		// Now, retrieve the created post by its ID
		ResponseEntity<Post> getResponse = restTemplate.getForEntity(
				"http://localhost:" + port + "/api/posts/" + postIdToRetrieve,
				Post.class
		);

		assertEquals(HttpStatus.OK, getResponse.getStatusCode());
		assertNotNull(getResponse.getBody());
		assertEquals("GetById Test Title", getResponse.getBody().getTitle());
		assertEquals("GetById Test Body", getResponse.getBody().getBody());
	}

	@Test
	void testGetPostByAuthorIdEndToEnd() {
		// Create a post to ensure there's something to retrieve by author
		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		Post post = new Post();
		post.setTitle("GetByAuthorId Test Title");
		post.setBody("GetByAuthorId Test Body");
		post.setAuthor(user);

		ResponseEntity<Post> createResponse = restTemplate.postForEntity(
				"http://localhost:" + port + "/api/posts",
				post,
				Post.class
		);

		assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
		assertNotNull(createResponse.getBody());
		Long authorIdToRetrieve = createResponse.getBody().getAuthor().getId();

		// Now, retrieve posts by the created author's ID
		ResponseEntity<List<Post>> getResponse = restTemplate.exchange(
				"http://localhost:" + port + "/api/posts/by-author/" + authorIdToRetrieve,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Post>>() {
				}
		);

		assertEquals(HttpStatus.OK, getResponse.getStatusCode());
		assertNotNull(getResponse.getBody());
		// Add assertions based on your actual implementation and data.
	}


}
