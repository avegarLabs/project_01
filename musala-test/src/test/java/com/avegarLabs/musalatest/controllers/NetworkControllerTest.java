package com.avegarLabs.musalatest.controllers;

import com.avegarLabs.musalatest.services.UserServicesImpementation;
import com.avegarLabs.musalatest.util.UserRequestInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(NetworkController.class)
class NetworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServicesImpementation userServices;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is Ok")
    void testAddFriendResponseIsSuccess() throws Exception {
        // Arrange
        int userId = 1;
        int friendId = 2;

        Mockito.doNothing().when(userServices).saveUserFiend(userId, friendId);

        // Act and Assert
        mockMvc.perform(post("/users/{userId}/friends/{friendId}", userId, friendId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(userServices, Mockito.times(1)).saveUserFiend(userId, friendId);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is Invalid")
    void testAddFriendInvalidRequest() throws Exception {
        // Arrange
        int userId = 1;
        int friendId = 2;

        Mockito.doThrow(new UserRequestInvalidException("Invalid request"))
                .when(userServices).saveUserFiend(userId, friendId);

        // Act and Assert
        mockMvc.perform(post("/users/{userId}/friends/{friendId}", userId, friendId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(userServices, Mockito.times(1)).saveUserFiend(userId, friendId);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is Internal Error")
    void testAddFriendInternalServerError() throws Exception {
        // Arrange
        int userId = 1;
        int friendId = 2;

        Mockito.doThrow(new RuntimeException("Internal Server Error"))
                .when(userServices).saveUserFiend(userId, friendId);

        // Act and Assert
        mockMvc.perform(post("/users/{userId}/friends/{friendId}", userId, friendId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(userServices, Mockito.times(1)).saveUserFiend(userId, friendId);
    }


    @Test
    @DisplayName("Test to validate that the response of the post method is Internal OK")
    void testAddFollowingResponseIsSuccess() throws Exception {
        // Arrange
        int userId = 1;
        int followerId = 2;

        Mockito.doNothing().when(userServices).saveFollowedToUser(userId, followerId);

        // Act and Assert
        mockMvc.perform(post("/users/{userId}/followers/{followerId}", userId, followerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(userServices, Mockito.times(1)).saveFollowedToUser(userId, followerId);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is Invalid")
    void testAddFollowingInvalidRequest() throws Exception {
        // Arrange
        int userId = 1;
        int followerId = 2;

        Mockito.doThrow(new UserRequestInvalidException("Invalid request"))
                .when(userServices).saveFollowedToUser(userId, followerId);

        // Act and Assert
        mockMvc.perform(post("/users/{userId}/followers/{followerId}", userId, followerId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(userServices, Mockito.times(1)).saveFollowedToUser(userId, followerId);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is Internal Error")
    void testAddFollowingInternalServerError() throws Exception {
        // Arrange
        int userId = 1;
        int followerId = 2;

        Mockito.doThrow(new RuntimeException("Internal Server Error"))
                .when(userServices).saveFollowedToUser(userId, followerId);

        // Act and Assert
        mockMvc.perform(post("/users/{userId}/followers/{followerId}", userId, followerId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Mockito.verify(userServices, Mockito.times(1)).saveFollowedToUser(userId, followerId);
    }

}