package co.edu.uniquindio.seci_proyect.repositories;


import co.edu.uniquindio.seci_proyect.Model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;
    private Notification testNotification;
    private String testReceiverId = "test-receiver-id";

    @BeforeEach
    void setUp() {
        notificationRepository.deleteAll();
        testNotification = new Notification();
        testNotification.setReceiverId(testReceiverId);
        testNotification.setRead(false);
        testNotification.setDate(LocalDateTime.now());
        testNotification.setMessage("Test notification");
        notificationRepository.save(testNotification);
    }

    @Test
    void findByReceiverId_ExistingReceiverId_ReturnsNotifications() {
        List<Notification> result = notificationRepository.findByReceiverId(testReceiverId);
        assertFalse(result.isEmpty());
        assertEquals(testReceiverId, result.get(0).getReceiverId());
    }



    @Test
    void countByReceiverIdAndReadFalse_ReturnsUnreadCount() {
        long count = notificationRepository.countByReceiverIdAndReadFalse(testReceiverId);
        assertEquals(1, count);
    }

    @Test
    void findLatestNotificationsByReceiver_ReturnsLimitedNotifications() {
        List<Notification> result = notificationRepository.findLatestNotificationsByReceiver(testReceiverId, 5);
        assertFalse(result.isEmpty());
    }
}