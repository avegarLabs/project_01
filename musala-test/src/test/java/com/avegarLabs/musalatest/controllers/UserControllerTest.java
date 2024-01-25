package com.avegarLabs.musalatest.controllers;

import com.avegarLabs.musalatest.dto.UserModel;
import com.avegarLabs.musalatest.dto.UserResponse;
import com.avegarLabs.musalatest.services.UserServicesImpementation;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServicesImpementation userServices;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is OK")
    void testPostResponseIsSuccess() throws Exception {
        // Arrange
        UserModel userModel = new UserModel();
        userModel.setFullName("Alfredo Vega Ramírez");

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setUseId(1);

        Mockito.when(userServices.createUser(userModel)).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userModel)))
                .andExpect(status().isOk());


        // Assert
        Mockito.verify(userServices, Mockito.times(1)).createUser(userModel);
    }

    @Test
    @DisplayName("Test to validate that the response of the post method is Bad")
    void testPostResponseIsBadRequest() throws Exception {
        // Arrange
        UserModel userModel = null;

        // Act
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userModel)))
                .andExpect(status().isBadRequest());

        // Assert
        Mockito.verify(userServices, Mockito.never()).createUser(userModel);
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