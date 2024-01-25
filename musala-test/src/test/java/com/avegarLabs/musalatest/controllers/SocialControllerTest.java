package com.avegarLabs.musalatest.controllers;

import com.avegarLabs.musalatest.dto.PostsListItems;
import com.avegarLabs.musalatest.services.PostServicesImplementation;
import com.avegarLabs.musalatest.util.PostRequestInvalidException;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(SocialController.class)
class SocialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostServicesImplementation postServices;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is OK")
    void testGetPostsToWallSuccessResponse() throws Exception {
        // Arrange
        int userId = 1;
        List<PostsListItems> expectedResponse = new ArrayList<>();

        Mockito.when(postServices.getPostsToWall(userId)).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(get("/walls/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assert
        Mockito.verify(postServices, Mockito.times(1)).getPostsToWall(userId);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is BAD")
    void testGetPostsToWallBadRequest() throws Exception {
        // Arrange
        int userId = 1;

        // Mock the exception to be thrown
        Mockito.when(postServices.getPostsToWall(userId)).thenThrow(new PostRequestInvalidException("Invalid request"));

        // Act
        mockMvc.perform(get("/walls/{userId}", userId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assert
        Mockito.verify(postServices, Mockito.times(1)).getPostsToWall(userId);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is OK")
    void testAddLikeIsSuccess() throws Exception {
        // Arrange
        int postId = 1;
        int userId = 2;

        // Act
        mockMvc.perform(post("/posts/{postId}/like/{userId}", postId, userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assert
        Mockito.verify(postServices, Mockito.times(1)).addLikeToPost(postId, userId);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is BAD")
    void testAddLikeIsBadRequest() throws Exception {
        // Arrange
        int postId = 1;
        int userId = 2;

        Mockito.doThrow(new UserRequestInvalidException("Invalid request"))
                .when(postServices).addLikeToPost(postId, userId);

        // Act
        mockMvc.perform(post("/posts/{postId}/like/{userId}", postId, userId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assert
        Mockito.verify(postServices, Mockito.times(1)).addLikeToPost(postId, userId);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is OK")
    void testRemoveLikeToPostResponseIsSuccess() throws Exception {
        // Arrange
        int postId = 1;
        int userId = 2;

        // Act
        mockMvc.perform(delete("/posts/{postId}/clear/{userId}", postId, userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assert
        Mockito.verify(postServices, Mockito.times(1)).removeLikeToPost(postId, userId);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is BAD")
    void testRemoveLikeToPostBadRequest() throws Exception {
        // Arrange
        int postId = 1;
        int userId = 2;

        // Mock the exception to be thrown
        Mockito.doThrow(new UserRequestInvalidException("Invalid request"))
                .when(postServices).removeLikeToPost(postId, userId);

        // Act
        mockMvc.perform(delete("/posts/{postId}/clear/{userId}", postId, userId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Assert
        Mockito.verify(postServices, Mockito.times(1)).removeLikeToPost(postId, userId);
    }

}