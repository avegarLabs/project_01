package com.avegarLabs.musalatest.services.abstracts;

import com.avegarLabs.musalatest.dto.UserModel;
import com.avegarLabs.musalatest.dto.UserResponse;
import com.avegarLabs.musalatest.models.Users;
import com.avegarLabs.musalatest.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@SpringBootTest
class UserServicesTest {

    @Autowired
    private UserServices service;

    @MockBean
    private UsersRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test to validate the method to create a new user")
    public void shouldSaveUserAndReturnMappedResponse() {
        // Arrange
        UserModel userModel = new UserModel();
        userModel.setFullName("Alfredo Vega");

        Mockito.when(repository.save(any(Users.class))).thenAnswer(invocation -> {
            Users userArgument = invocation.getArgument(0);
            userArgument.setId(1);
            return userArgument;
        });

        // Act
        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setUseId(1);
        UserResponse actualResponse = service.createUser(userModel);

        // Assert
        Mockito.verify(repository, Mockito.times(1)).save(any(Users.class));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Test to validate the method to add a friend to a user")
    public void shouldAddFriendsAndSave() {
        // Arrange
        int userId = 1;
        int friendId = 2;

        Users user = new Users();
        user.setId(userId);


        Users friend = new Users();
        friend.setId(friendId);

        Mockito.when(repository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(repository.getReferenceById(friendId)).thenReturn(friend);

        // Act
        service.saveUserFiend(userId, friendId);

        // Assert
        Mockito.verify(repository, Mockito.times(2)).save(any(Users.class));
        assertEquals(1, user.getFriends().size());
        assertTrue(user.getFriends().contains(friend));
        assertEquals(1, friend.getFriends().size());
        assertTrue(friend.getFriends().contains(user));
    }

    @Test
    @DisplayName("Test to validate the method to add the user that is followed within the social network")
    void shouldSaveFollowedAndUser() {
        // Arrange
        int userId = 1;
        int followerId = 2;

        Users user = new Users();
        user.setId(userId);

        Users follower = new Users();
        follower.setId(followerId);

        Mockito.when(repository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(repository.getReferenceById(followerId)).thenReturn(follower);

        // Act
        service.saveFollowedToUser(userId, followerId);

        // Assert
        Mockito.verify(repository, Mockito.times(1)).save(any(Users.class));
        assertEquals(1, follower.getFollowed().size());
        assertTrue(follower.getFollowed().contains(user));
    }


}