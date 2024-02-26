package com.leomottadev.desafiopicpaysimplificado.services;

import com.leomottadev.desafiopicpaysimplificado.domain.user.User;
import com.leomottadev.desafiopicpaysimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private RestTemplate restTemplate;

    @Value("${app.notificationApi}")
    private String notificationApi;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity(notificationApi, notificationRequest, String.class);

        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
            throw new Exception("Serviço de notificação está fora do ar");
        }
    }
}
