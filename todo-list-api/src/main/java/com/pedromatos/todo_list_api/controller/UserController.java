package com.pedromatos.todo_list_api.controller;

import com.pedromatos.todo_list_api.dto.UserRegistrationDTO;
import com.pedromatos.todo_list_api.dto.UserResponseDTO;
import com.pedromatos.todo_list_api.model.User;
import com.pedromatos.todo_list_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService service){
        this.userService = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO request, UriComponentsBuilder uriBuilder){
           UserResponseDTO userCreated = userService.registerUser(request);

        URI uri = uriBuilder
                .path("/{id}")
                .buildAndExpand(userCreated.id())
                .toUri();

            return ResponseEntity.created(uri).body(userCreated);


    }

}
