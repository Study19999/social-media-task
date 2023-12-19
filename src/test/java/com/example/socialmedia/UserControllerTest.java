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

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void testCreateUser() throws Exception {
        User user = new User();
        user.setUsername("testUser");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void testGetUserByIdFound() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");

        Mockito.when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        Long userId = 1L;

        Mockito.when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserByUsernameFound() throws Exception {
        String username = "testUser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        Mockito.when(userService.getUserByUsername(username)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/by-username/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void testGetUserByUsernameNotFound() throws Exception {
        String username = "nonexistentUser";

        Mockito.when(userService.getUserByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/by-username/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
