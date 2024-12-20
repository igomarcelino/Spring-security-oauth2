package com.igomarcelino.demo_oauth_security.dto;

public record LoginResponseDTO(String accessToken, Long expiresIn) {
}
