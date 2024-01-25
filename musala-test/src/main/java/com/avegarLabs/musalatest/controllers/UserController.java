package com.avegarLabs.musalatest.controllers;

import com.avegarLabs.musalatest.dto.UserResponse;
import com.avegarLabs.musalatest.dto.UserModel;
import com.avegarLabs.musalatest.services.UserServicesImpementation;
import com.avegarLabs.musalatest.util.ErrorMessage;
import com.avegarLabs.musalatest.util.UserRequestInvalidException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints for managing users")
public class UserController {

    @Autowired
    UserServicesImpementation service;

    @PostMapping("/users")
    public ResponseEntity<Object> post(@RequestBody UserModel model) {
        try {
            UserResponse userListItem = service.createUser(model);
            return ResponseEntity.status(HttpStatus.OK).body(userListItem);
        } catch (UserRequestInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Bad Request"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }
}
