package com.avegarLabs.musalatest.controllers;

import com.avegarLabs.musalatest.dto.PostModel;
import com.avegarLabs.musalatest.dto.PostResponse;
import com.avegarLabs.musalatest.dto.PostsListItems;
import com.avegarLabs.musalatest.services.PostServicesImplementation;
import com.avegarLabs.musalatest.util.ErrorMessage;
import com.avegarLabs.musalatest.util.PostRequestInvalidException;
import com.avegarLabs.musalatest.util.SuccessMessage;
import com.avegarLabs.musalatest.util.UserRequestInvalidException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Social", description = "Endpoints for managing the social relations of users, posts of friends and it follow, likes and dislikes")
public class SocialController {

    @Autowired
    PostServicesImplementation service;

    @GetMapping("/walls/{userId}")
    public ResponseEntity<Object> getPost(@PathVariable int userId) {
        try {
            List<PostsListItems> postListItem = service.getPostsToWall(userId);
            return ResponseEntity.status(HttpStatus.OK).body(postListItem);
        } catch (PostRequestInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Bad Request"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @PostMapping("/posts/{postId}/like/{userId}")
    public ResponseEntity<Object> addLike(@PathVariable int postId, @PathVariable int userId) {
        try {
            service.addLikeToPost(postId, userId);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully completed the operation."));
        } catch (UserRequestInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Bad Request"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @DeleteMapping("/posts/{postId}/clear/{userId}")
    public ResponseEntity<Object> remove(@PathVariable int postId, @PathVariable int userId) {
        try {
            service.removeLikeToPost(postId, userId);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully completed the operation."));
        } catch (UserRequestInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Bad Request"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

}
