package com.example.socialmedia;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void createUser() {
        // When
        User user = new User();
        user.setUsername("testUser");

        // Then
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void updateUsername() {
        // Given
        User user = new User();
        user.setUsername("OriginalUsername");

        // When
        user.setUsername("UpdatedUsername");

        // Then
        assertEquals("UpdatedUsername", user.getUsername());
    }
    @Test
    public void testAllArgsConstructor() {
        // When
        User user = new User(1L, "testUser");

        // Then
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
    }
}

