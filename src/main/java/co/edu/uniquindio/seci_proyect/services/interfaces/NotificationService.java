package co.edu.uniquindio.seci_proyect.services.interfaces;

import co.edu.uniquindio.seci_proyect.dtos.notification.CreateNotificationDTO;
import co.edu.uniquindio.seci_proyect.dtos.notification.NotificationResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationService {
    NotificationResponse createNotification(CreateNotificationDTO request);
    List<NotificationResponse> getNotificationsByReceiver(String receiverId);
    Page<NotificationResponse> getNotificationsByReceiver(String receiverId, int page, int size);
    List<NotificationResponse> getUnreadNotifications(String receiverId);
    long countUnreadNotifications(String receiverId);
    NotificationResponse markAsRead(String notificationId);
    List<NotificationResponse> getLatestNotifications(String receiverId, int limit);
}