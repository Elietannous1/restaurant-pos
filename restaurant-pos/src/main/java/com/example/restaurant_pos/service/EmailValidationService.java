package com.example.restaurant_pos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmailValidationService {

    @Value("${zerobounce.api.key}")
    private String apiKey;

    @Value("${zerobounce.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean isEmailValid(String email) {
        String url = String.format("%s?api_key=%s&email=%s", apiUrl, apiKey, email);
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getBody() != null) {
            String status = (String) response.getBody().get("status");
            return "valid".equalsIgnoreCase(status); // ZeroBounce returns "valid" for real emails
        }

        return false;
    }
}
