package com.avegarLabs.musalatest.controllers;

import com.avegarLabs.musalatest.dto.PostModel;
import com.avegarLabs.musalatest.dto.PostResponse;
import com.avegarLabs.musalatest.services.PostServicesImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(PostsController.class)
class PostsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostServicesImplementation postServices;

    @BeforeEach
    void setUp() {
    }


    @Test
    @DisplayName("Test to validate that the response of the post method is OK")
    void testPostSuccess() throws Exception {
        // Arrange
        PostModel postModel = new PostModel();
        postModel.setText("Lorem ipsum");
        postModel.setVisibility("public");
        postModel.setUserId(1);

        PostResponse expectedResponse = new PostResponse();
        expectedResponse.setPostId(1);

        Mockito.when(postServices.createPost(postModel)).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postModel)))
                .andExpect(status().isOk());

        // Assert
        Mockito.verify(postServices, Mockito.times(1)).createPost(postModel);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is Bad")
    void testPostBadRequest() throws Exception {
        // Arrange
        PostModel postModel = null;

        // Act
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postModel)))
                .andExpect(status().isBadRequest());

        // Assert
        Mockito.verify(postServices, Mockito.never()).createPost(postModel);
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}