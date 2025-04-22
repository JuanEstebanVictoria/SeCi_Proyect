package co.edu.uniquindio.seci_proyect.dtos.user;

import jakarta.validation.constraints.*;

public record PasswordResetRequest(
        @NotBlank String resetCode,
        @NotBlank String newPassword
) {}