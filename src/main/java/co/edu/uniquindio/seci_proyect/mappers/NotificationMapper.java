package co.edu.uniquindio.seci_proyect.mappers;

import co.edu.uniquindio.seci_proyect.Model.Notification;
import co.edu.uniquindio.seci_proyect.dtos.notification.NotificationResponse;

public interface NotificationMapper {
    NotificationResponse toResponse(Notification notification);
}