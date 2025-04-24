package co.edu.uniquindio.seci_proyect.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String email, @NotBlank String password) {
}