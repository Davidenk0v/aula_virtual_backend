package com.grupo2.aulavirtual.payload.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "User name cannot be blank") String username,
        @NotBlank(message = "User password cannot be blank") String password) {
}
