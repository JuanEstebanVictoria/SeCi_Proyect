package co.edu.uniquindio.seci_proyect.services.integration;


import co.edu.uniquindio.seci_proyect.Model.Notification;
import co.edu.uniquindio.seci_proyect.dtos.notification.CreateNotificationDTO;
import co.edu.uniquindio.seci_proyect.dtos.notification.NotificationResponse;
import co.edu.uniquindio.seci_proyect.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.seci_proyect.mappers.NotificationMapper;
import co.edu.uniquindio.seci_proyect.repositories.NotificationRepository;
import co.edu.uniquindio.seci_proyect.services.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private Notification testNotification;
    private CreateNotificationDTO createDTO;
    private NotificationResponse responseDTO;

    @BeforeEach
    void setUp() {
        testNotification = new Notification();
        testNotification.setId(UUID.randomUUID().toString());
        testNotification.setMessage("Test message");
        testNotification.setDate(LocalDateTime.now());
        testNotification.setRead(false);
        testNotification.setReceiverId("user123");

        createDTO = new CreateNotificationDTO(
                "Test message",
                "user123",
                null
        );


    }



    @Test
    void getNotificationsByReceiver_ExistingReceiver_ReturnsList() {
        when(notificationRepository.findByReceiverId(anyString()))
                .thenReturn(Collections.singletonList(testNotification));
        when(notificationMapper.toNotificationResponse(any(Notification.class)))
                .thenReturn(responseDTO);

        List<NotificationResponse> result =
                notificationService.getNotificationsByReceiver("user123");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testNotification.getId(), result.get(0).id());
    }

    @Test
    void getNotificationsByReceiverPaged_ReturnsPagedResults() {
        Page<Notification> page = new PageImpl<>(Collections.singletonList(testNotification));
        when(notificationRepository.findByReceiverId(anyString(), any(PageRequest.class)))
                .thenReturn(page);
        when(notificationMapper.toNotificationResponse(any(Notification.class)))
                .thenReturn(responseDTO);

        Page<NotificationResponse> result =
                notificationService.getNotificationsByReceiver("user123", 0, 10);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getUnreadNotifications_ReturnsOnlyUnread() {
        when(notificationRepository.findByReceiverIdAndReadFalse(anyString()))
                .thenReturn(Collections.singletonList(testNotification));
        when(notificationMapper.toNotificationResponse(any(Notification.class)))
                .thenReturn(responseDTO);

        List<NotificationResponse> result =
                notificationService.getUnreadNotifications("user123");

        assertFalse(result.isEmpty());
        assertFalse(result.get(0).read());
    }

    @Test
    void countUnreadNotifications_ReturnsCorrectCount() {
        when(notificationRepository.countByReceiverIdAndReadFalse(anyString()))
                .thenReturn(1L);

        long count = notificationService.countUnreadNotifications("user123");

        assertEquals(1, count);
    }



    @Test
    void markAsRead_NonExistingNotification_ThrowsException() {
        when(notificationRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                notificationService.markAsRead("nonexistent"));
    }

    @Test
    void getLatestNotifications_ReturnsLimitedResults() {
        when(notificationRepository.findLatestNotificationsByReceiver(anyString(), anyInt()))
                .thenReturn(Collections.singletonList(testNotification));
        when(notificationMapper.toNotificationResponse(any(Notification.class)))
                .thenReturn(responseDTO);

        List<NotificationResponse> result =
                notificationService.getLatestNotifications("user123", 5);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}