package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.Model.Notification;
import co.edu.uniquindio.seci_proyect.dtos.notification.CreateNotificationDTO;
import co.edu.uniquindio.seci_proyect.dtos.notification.NotificationResponse;
import co.edu.uniquindio.seci_proyect.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.seci_proyect.mappers.NotificationMapper;
import co.edu.uniquindio.seci_proyect.repositories.NotificationRepository;
import co.edu.uniquindio.seci_proyect.services.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public NotificationResponse createNotification(CreateNotificationDTO request) {
        Notification notification = notificationMapper.toNotification(request);
        Notification savedNotification = notificationRepository.save(notification);
        return notificationMapper.toNotificationResponse(savedNotification);
    }

    @Override
    public List<NotificationResponse> getNotificationsByReceiver(String receiverId) {
        return notificationRepository.findByReceiverId(receiverId).stream()
                .map(notificationMapper::toNotificationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<NotificationResponse> getNotificationsByReceiver(String receiverId, int page, int size) {
        return notificationRepository.findByReceiverId(receiverId, PageRequest.of(page, size))
                .map(notificationMapper::toNotificationResponse);
    }

    @Override
    public List<NotificationResponse> getUnreadNotifications(String receiverId) {
        return notificationRepository.findByReceiverIdAndReadFalse(receiverId).stream()
                .map(notificationMapper::toNotificationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public long countUnreadNotifications(String receiverId) {
        return notificationRepository.countByReceiverIdAndReadFalse(receiverId);
    }

    @Override
    @Transactional
    public NotificationResponse markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada"));

        notification.setRead(true);
        Notification updatedNotification = notificationRepository.save(notification);

        return notificationMapper.toNotificationResponse(updatedNotification);
    }

    @Override
    public List<NotificationResponse> getLatestNotifications(String receiverId, int limit) {
        return notificationRepository.findLatestNotificationsByReceiver(receiverId, limit).stream()
                .map(notificationMapper::toNotificationResponse)
                .collect(Collectors.toList());
    }
}