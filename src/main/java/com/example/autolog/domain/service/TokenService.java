package com.example.autolog.domain.service;

import com.example.autolog.infrastructure.persistence.entity.UserEntity;

public interface TokenService {

    String generateAccessToken(UserEntity user);

    String validateAccessToken(String token);

    String generatePasswordResetToken(UserEntity user);

    boolean validatePasswordResetToken(String token);

    String extractEmailFromToken(String token);
}
