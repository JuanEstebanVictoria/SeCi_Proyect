package co.edu.uniquindio.seci_proyect.services.integration;

import co.edu.uniquindio.seci_proyect.dtos.notification.CreateNotificationDTO;
import co.edu.uniquindio.seci_proyect.dtos.notification.NotificationResponse;
import co.edu.uniquindio.seci_proyect.services.interfaces.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private NotificationService notificationService;

    @Test
    void createNotification_ShouldReturnCreatedNotification() throws Exception {
        CreateNotificationDTO request = new CreateNotificationDTO(
                "Test message", "user123", null);

        NotificationResponse response = new NotificationResponse(
                UUID.randomUUID().toString(),
                "Test message",
                LocalDateTime.now(),
                false,
                null,
                null
        );

        when(notificationService.createNotification(any(CreateNotificationDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.message").value("Test message"));
    }

    @Test
    void getNotificationsByReceiver_ShouldReturnNotifications() throws Exception {
        NotificationResponse response = new NotificationResponse(
                UUID.randomUUID().toString(),
                "Test message",
                LocalDateTime.now(),
                false,
                null,
                null
        );

        when(notificationService.getNotificationsByReceiver(anyString()))
                .thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/api/notifications/user/user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].message").value("Test message"));
    }

    @Test
    void getUnreadNotifications_ShouldReturnUnreadOnly() throws Exception {
        NotificationResponse response = new NotificationResponse(
                UUID.randomUUID().toString(),
                "Test message",
                LocalDateTime.now(),
                false,
                null,
                null
        );

        when(notificationService.getUnreadNotifications(anyString()))
                .thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/api/notifications/user/user123/unread"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].read").value(false));
    }

    @Test
    void countUnreadNotifications_ShouldReturnCount() throws Exception {
        when(notificationService.countUnreadNotifications(anyString()))
                .thenReturn(1L);

        mockMvc.perform(get("/api/notifications/user/user123/unread/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void markAsRead_ShouldUpdateNotification() throws Exception {
        NotificationResponse response = new NotificationResponse(
                UUID.randomUUID().toString(),
                "Test message",
                LocalDateTime.now(),
                true,
                null,
                null
        );

        when(notificationService.markAsRead(anyString()))
                .thenReturn(response);

        mockMvc.perform(put("/api/notifications/{notificationId}/read", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.read").value(true));
    }

    @Test
    void getLatestNotifications_ShouldReturnLimitedResults() throws Exception {
        NotificationResponse response = new NotificationResponse(
                UUID.randomUUID().toString(),
                "Test message",
                LocalDateTime.now(),
                false,
                null,
                null
        );

        when(notificationService.getLatestNotifications(anyString(), anyInt()))
                .thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/api/notifications/user/user123/latest?limit=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }
}