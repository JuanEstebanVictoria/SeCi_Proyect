package co.edu.uniquindio.seci_proyect.mappers;

import co.edu.uniquindio.seci_proyect.Model.Notification;
import co.edu.uniquindio.seci_proyect.dtos.notification.CreateNotificationDTO;
import co.edu.uniquindio.seci_proyect.dtos.notification.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "read", constant = "false")
    Notification toNotification(CreateNotificationDTO request);
    NotificationResponse toNotificationResponse(Notification notification);
}