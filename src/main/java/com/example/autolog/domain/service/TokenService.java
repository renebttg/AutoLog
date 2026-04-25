package com.example.autolog.domain.service;

import com.example.autolog.infrastructure.persistence.entity.UserEntity;

public interface TokenService {

    String generateToken(UserEntity user);
}
