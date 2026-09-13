package com.pedromatos.todo_list_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = false)
public record RegistrationRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String name,
        @NotBlank String password
) {

}

