package com.lugui14.simplified_transactions.unit.notification.services;

import com.lugui14.simplified_transactions.notification.domain.NotificationDto;
import com.lugui14.simplified_transactions.notification.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @InjectMocks
    private NotificationService notificationService;

    private final String mockUrl = "mock-notification-service-url";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);

        notificationService = new NotificationService(restTemplateBuilder);

        try {
            var field = NotificationService.class.getDeclaredField("notificationServiceURL");
            field.setAccessible(true);
            field.set(notificationService, mockUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set mock URL", e);
        }
    }

    @Test
    @DisplayName("Should send notification successfully")
    void shouldSendNotificationSuccessfully() {
        NotificationDto dto = new NotificationDto(1, "Transaction completed successfully");

        when(restTemplate.postForEntity(mockUrl, dto, void.class)).thenReturn(null);

        assertDoesNotThrow(() -> notificationService.sendNotification(dto));

        verify(restTemplate, times(1)).postForEntity(mockUrl, dto, void.class);
    }

    @Test
    @DisplayName("Should throw exception if notification fails")
    void shouldThrowExceptionIfNotificationFails() {
        NotificationDto dto = new NotificationDto(1, "Transaction failed");

        doThrow(new RestClientException("Connection error"))
                .when(restTemplate).postForEntity(mockUrl, dto, void.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationService.sendNotification(dto));

        assertEquals("Error sending notification", exception.getMessage());
    }
}
