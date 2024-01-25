package com.avegarLabs.musalatest.services.abstracts;

import com.avegarLabs.musalatest.dto.PostModel;
import com.avegarLabs.musalatest.dto.PostResponse;
import com.avegarLabs.musalatest.dto.PostsListItems;
import com.avegarLabs.musalatest.models.Posts;
import com.avegarLabs.musalatest.models.Users;
import com.avegarLabs.musalatest.repositories.PostsRepository;
import com.avegarLabs.musalatest.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@SpringBootTest
class PostsServicesTest {
    @Autowired
    private PostsServices service;

    @MockBean
    private PostsRepository repository;

    @MockBean
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test to validate the method to create a new post")
    void shouldCreatePostAndReturnMappedResponse() {
        // Arrange
        PostModel postModel = new PostModel();
        postModel.setText("Test post");
        postModel.setVisibility("public");
        postModel.setUserId(1);


        Mockito.when(repository.save(any(Posts.class))).thenAnswer(invocation -> {
            Posts postsArgument = invocation.getArgument(0);
            postsArgument.setId(1);
            return postsArgument;
        });

        // Act
        PostResponse expectedResponse = new PostResponse();
        expectedResponse.setPostId(1);
        PostResponse postResponse = service.createPost(postModel);

        // Assert
        Mockito.verify(repository, Mockito.times(1)).save(any(Posts.class));
        assertEquals(expectedResponse, postResponse);
    }

    @Test
    @Transactional
    @DisplayName("Test to validate the method to add a like to post")
    void shouldAddLikeAndSavePost() {
        // Arrange
        int postId = 1;
        int userId = 2;

        Users user = Mockito.mock(Users.class);
        Mockito.when(user.getId()).thenReturn(userId);

        Posts post = new Posts();
        post.setId(postId);
        post.setLikes(new HashSet<>());

        Mockito.when(usersRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(repository.getReferenceById(postId)).thenReturn(post);

        // Act
        service.addLikeToPost(postId, userId);

        // Assert
        Mockito.verify(repository, Mockito.times(1)).save(post);
        assertTrue(post.getLikes().contains(user));
    }

    @Test
    @Transactional
    @DisplayName("Test to validate the method to remove a like")
    void shouldRemoveLikeAndSavePost() {
        // Arrange
        int postId = 1;
        int userId = 2;

        Users user = Mockito.mock(Users.class);
        Mockito.when(user.getId()).thenReturn(userId);

        Posts post = new Posts();
        post.setId(postId);
        Set<Users> likes = new HashSet<>();
        likes.add(user);
        post.setLikes(likes);

        Mockito.when(usersRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(repository.getReferenceById(postId)).thenReturn(post);

        // Act
        service.removeLikeToPost(postId, userId);

        // Assert
        Mockito.verify(repository, Mockito.times(1)).removeLikeNative(postId, userId);
        assertFalse(post.getLikes().contains(user));
    }

    @Test
    @DisplayName("Test to validate the method that shows all the posts on my wall")
    void shouldGetPostsToWall() {
        // Arrange
        int userId = 1;

        Users user = new Users();
        user.setId(userId);
        user.setFriends(new ArrayList<>());
        user.setFollowed(new ArrayList<>());

        Posts post1 = new Posts();
        post1.setText("Post 1");
        post1.setPostedOn(LocalDateTime.now().minusHours(1));
        post1.setUser(user);
        post1.setVisibility("public");

        Posts post2 = new Posts();
        post2.setText("Post 2");
        post2.setPostedOn(LocalDateTime.now().minusHours(2));
        post2.setUser(user);
        post2.setVisibility("public");

        Mockito.when(usersRepository.getReferenceById(userId)).thenReturn(user);

        Mockito.when(repository.getPostsByUserId(userId)).thenReturn(List.of(post1, post2));

        // Act
        List<PostsListItems> result = service.getPostsToWall(userId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getText());
        assertEquals("Post 2", result.get(1).getText());
    }
}