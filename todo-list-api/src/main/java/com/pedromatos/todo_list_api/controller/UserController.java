package com.pedromatos.todo_list_api.controller;

import com.pedromatos.todo_list_api.dto.LoginRequestDTO;
import com.pedromatos.todo_list_api.dto.LoginResponseDTO;
import com.pedromatos.todo_list_api.dto.RegistrationRequestDTO;
import com.pedromatos.todo_list_api.dto.RegistrationResponseDTO;
import com.pedromatos.todo_list_api.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<RegistrationResponseDTO> registerUser(@Valid @RequestBody RegistrationRequestDTO request, UriComponentsBuilder uriBuilder){
           RegistrationResponseDTO userCreated = userService.registerUser(request);

        URI uri = uriBuilder
                .path("/{id}")
                .buildAndExpand(userCreated.id())
                .toUri();

            return ResponseEntity.created(uri).body(userCreated);


    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginDTO){
        return ResponseEntity.ok().body(userService.loginUser(loginDTO));
    }

}
