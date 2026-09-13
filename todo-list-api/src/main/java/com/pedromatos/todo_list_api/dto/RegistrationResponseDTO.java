package com.pedromatos.todo_list_api.dto;

public record RegistrationResponseDTO(Long id, String name, String email, String token) {
}