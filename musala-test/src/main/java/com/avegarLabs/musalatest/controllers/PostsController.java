package com.avegarLabs.musalatest.controllers;

import com.avegarLabs.musalatest.dto.PostResponse;
import com.avegarLabs.musalatest.dto.PostModel;
import com.avegarLabs.musalatest.services.PostServicesImplementation;
import com.avegarLabs.musalatest.util.ErrorMessage;
import com.avegarLabs.musalatest.util.PostRequestInvalidException;
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
@Tag(name = "Posts", description = "Endpoints for managing user posts")
public class PostsController {

    @Autowired
    PostServicesImplementation service;

    @PostMapping("/posts")
    public ResponseEntity<Object> post(@RequestBody PostModel model) {
        try {
            PostResponse postListItem = service.createPost(model);
            return ResponseEntity.status(HttpStatus.OK).body(postListItem);
        } catch (PostRequestInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Bad Request"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal Server Error"));
        }
    }
}
