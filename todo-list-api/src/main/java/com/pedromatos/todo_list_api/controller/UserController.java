package com.pedromatos.todo_list_api.controller;

import com.pedromatos.todo_list_api.dto.UserResponseDTO;
import com.pedromatos.todo_list_api.model.User;
import com.pedromatos.todo_list_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService service){
        this.userService = service;
    }

    @PostMapping("/register")
    public void registerUser() {}

    @GetMapping
    public ResponseEntity<UserResponseDTO> findByEmail(@RequestParam(required = true) String email){
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(new UserResponseDTO(user.getEmail(), user.getName()));
    }
}
