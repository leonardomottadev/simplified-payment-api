package com.leomottadev.desafiopicpaysimplificado.services;

import com.leomottadev.desafiopicpaysimplificado.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AuthorizationService {
    private final RestTemplate restTemplate;

    @Value("${app.authorizationApi}")
    private String authorizationApi;

    public AuthorizationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(authorizationApi, Map.class);


        if(authorizationResponse.getStatusCode() == HttpStatus.OK && authorizationResponse.getBody() != null) {
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
