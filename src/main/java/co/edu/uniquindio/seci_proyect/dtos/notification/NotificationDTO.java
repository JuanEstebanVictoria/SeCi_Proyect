package co.edu.uniquindio.seci_proyect.dtos.notification;

import jakarta.validation.constraints.NotBlank;

public record NotificationDTO(
        @NotBlank String message,
        @NotBlank String receiverId
) {}