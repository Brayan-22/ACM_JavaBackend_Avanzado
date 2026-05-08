package com.acm.microservicio2;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String extractUsername(String token) {
        try {
            return (String) extractAllClaims(token).get("sub");
        } catch (Exception e) {
            log.warn("Error extracting username from token", e);
            return null;
        }
    }

    public Map<String, Object> extractAllClaims(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Token inválido");
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            return objectMapper.readValue(payload, Map.class);
        } catch (Exception e) {
            log.warn("Error extracting claims from token", e);
            return Map.of();
        }
    }

    public Long extractExpirationTime(String token) {
        try {
            return ((Number) extractAllClaims(token).get("exp")).longValue() * 1000;
        } catch (Exception e) {
            log.warn("Error extracting expiration from token", e);
            return null;
        }
    }
}
