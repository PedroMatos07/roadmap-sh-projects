package com.pedromatos.todo_list_api.service;

import com.pedromatos.todo_list_api.model.User;
import com.pedromatos.todo_list_api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public User findByEmail(String email){
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with that email was not found"));
    }
}
