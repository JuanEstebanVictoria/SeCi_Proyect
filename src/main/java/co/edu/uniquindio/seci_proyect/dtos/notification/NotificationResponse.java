package co.edu.uniquindio.seci_proyect.dtos.notification;

import java.time.LocalDateTime;

public record NotificationResponse(
        String id,
        String message,
        LocalDateTime date,
        boolean read,
        String reportId,
        String reportTitle
) {}