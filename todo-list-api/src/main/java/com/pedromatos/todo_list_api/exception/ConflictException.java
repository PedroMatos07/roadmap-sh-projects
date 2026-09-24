package com.pedromatos.todo_list_api.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message){
        super(message);
    }
}
