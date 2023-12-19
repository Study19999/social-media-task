package com.example.socialmedia;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setUsername("testUser");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void testGetUserByUsername() {
        String username = "testUser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByUsername(username);

        assertTrue(foundUser.isPresent());
        assertEquals(username, foundUser.get().getUsername());
    }

}
