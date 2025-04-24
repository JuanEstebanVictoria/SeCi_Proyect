package co.edu.uniquindio.seci_proyect.dtos.user;

import jakarta.validation.constraints.*;

public record PasswordResetRequest(
        @NotBlank String email,
        @NotBlank String code,
        @NotBlank @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") String newPassword
) {}