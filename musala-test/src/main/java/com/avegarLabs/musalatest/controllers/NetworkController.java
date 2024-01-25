package com.avegarLabs.musalatest.controllers;

import com.avegarLabs.musalatest.services.UserServicesImpementation;
import com.avegarLabs.musalatest.util.ErrorMessage;
import com.avegarLabs.musalatest.util.SuccessMessage;
import com.avegarLabs.musalatest.util.UserRequestInvalidException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Network", description = "Endpoints for managing relations user and friends - user and user that  it follow")
public class NetworkController {

    @Autowired
    UserServicesImpementation service;

    @PostMapping("/users/{userId}/friends/{friendId}")
    public ResponseEntity<Object> addFriend(@PathVariable int userId, @PathVariable int friendId) {
        try {
            service.saveUserFiend(userId, friendId);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully completed the operation."));
        } catch (UserRequestInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Bad Request"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }

    @PostMapping("/users/{userId}/followers/{followerId}")
    public ResponseEntity<Object> addFollower(@PathVariable int userId, @PathVariable int followerId) {
        try {
            service.saveFollowedToUser(userId, followerId);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Successfully completed the operation."));
        } catch (UserRequestInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Bad Request"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }
}
