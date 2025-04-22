package co.edu.uniquindio.seci_proyect.dtos.notification;

public record CreateNotificationDTO(
        String message,
        String receiverId,
        String reportId // Opcional
) {}