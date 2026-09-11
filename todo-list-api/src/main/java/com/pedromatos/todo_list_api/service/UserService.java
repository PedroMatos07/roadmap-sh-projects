package com.pedromatos.todo_list_api.service;

import com.pedromatos.todo_list_api.dto.UserRegistrationDTO;
import com.pedromatos.todo_list_api.dto.UserResponseDTO;
import com.pedromatos.todo_list_api.model.User;
import com.pedromatos.todo_list_api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository repository){
        this.userRepository = repository;
    }

    public UserResponseDTO registerUser(UserRegistrationDTO request){
        User newUser = new User(null, request.name(), request.email(), request.password());

        User savedUser = userRepository.save(newUser);

        return new UserResponseDTO(savedUser.getId(),savedUser.getName(),savedUser.getEmail());
    }
}
