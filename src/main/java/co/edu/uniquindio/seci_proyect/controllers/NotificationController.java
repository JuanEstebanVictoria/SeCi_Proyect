package co.edu.uniquindio.seci_proyect.controllers;


import co.edu.uniquindio.seci_proyect.services.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uniquindio.seci_proyect.dtos.notification.CreateNotificationDTO;
import co.edu.uniquindio.seci_proyect.dtos.notification.NotificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @RequestBody CreateNotificationDTO request) {
        NotificationResponse response = notificationService.createNotification(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{receiverId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByReceiver(
            @PathVariable String receiverId) {
        List<NotificationResponse> notifications =
                notificationService.getNotificationsByReceiver(receiverId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{receiverId}/paged")
    public ResponseEntity<Page<NotificationResponse>> getNotificationsByReceiverPaged(
            @PathVariable String receiverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<NotificationResponse> notifications =
                notificationService.getNotificationsByReceiver(receiverId, page, size);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{receiverId}/unread")
    public ResponseEntity<List<NotificationResponse>> getUnreadNotifications(
            @PathVariable String receiverId) {
        List<NotificationResponse> notifications =
                notificationService.getUnreadNotifications(receiverId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{receiverId}/unread/count")
    public ResponseEntity<Long> countUnreadNotifications(
            @PathVariable String receiverId) {
        long count = notificationService.countUnreadNotifications(receiverId);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationResponse> markAsRead(
            @PathVariable String notificationId) {
        NotificationResponse response = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{receiverId}/latest")
    public ResponseEntity<List<NotificationResponse>> getLatestNotifications(
            @PathVariable String receiverId,
            @RequestParam(defaultValue = "5") int limit) {
        List<NotificationResponse> notifications =
                notificationService.getLatestNotifications(receiverId, limit);
        return ResponseEntity.ok(notifications);
    }
}
