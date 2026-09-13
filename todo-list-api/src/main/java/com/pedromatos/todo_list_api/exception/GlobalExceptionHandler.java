package com.pedromatos.todo_list_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleUserNotFound(){
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }

}
