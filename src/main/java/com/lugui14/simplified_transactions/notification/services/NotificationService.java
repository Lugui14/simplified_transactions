package com.lugui14.simplified_transactions.notification.services;

import com.lugui14.simplified_transactions.notification.domain.NotificationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;

    @Value("${mock.send_notification.url}")
    private String notificationServiceURL;

    public NotificationService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendNotification(NotificationDto notificationDto) {
        try {
            restTemplate.postForEntity(notificationServiceURL, notificationDto, void.class);

            System.out.println(notificationDto.userId() + ": " + notificationDto.message());
        } catch (RestClientException e) {
            throw new RuntimeException("Error sending notification");
        }
    }

}
