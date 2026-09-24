package com.pedromatos.todo_list_api.service;

import com.pedromatos.todo_list_api.dto.LoginRequestDTO;
import com.pedromatos.todo_list_api.dto.LoginResponseDTO;
import com.pedromatos.todo_list_api.dto.RegistrationRequestDTO;
import com.pedromatos.todo_list_api.dto.RegistrationResponseDTO;
import com.pedromatos.todo_list_api.exception.ConflictException;
import com.pedromatos.todo_list_api.exception.InvalidCredentialsException;
import com.pedromatos.todo_list_api.model.User;
import com.pedromatos.todo_list_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder enconder;
    private final JwtService jwtService;

    public UserService(UserRepository repository, PasswordEncoder enconder, JwtService jwtService){
        this.userRepository = repository;
        this.enconder = enconder;
        this.jwtService = jwtService;

    }

    public RegistrationResponseDTO registerUser(RegistrationRequestDTO request){

        if(userRepository.findByEmail(request.email()).isPresent()){
            throw new ConflictException("User already exist");
        }

        User newUser = new User(null, request.name(), request.email(), enconder.encode(request.password()));

        User savedUser = userRepository.save(newUser);

        String token = jwtService.generateToken(savedUser);

        return new RegistrationResponseDTO(savedUser.getId(),savedUser.getName(),savedUser.getEmail(), token );
    }

    public LoginResponseDTO loginUser(LoginRequestDTO requestLogin){
        Optional<User> user = userRepository.findByEmail(requestLogin.email());
        if(user.isEmpty()){
          throw new InvalidCredentialsException();
        }

        if (!enconder.matches(requestLogin.password(), user.get().getPassword())){
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user.get());
        return new LoginResponseDTO(token);
    }
}
