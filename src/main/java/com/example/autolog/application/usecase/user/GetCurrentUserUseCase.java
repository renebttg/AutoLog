package com.example.autolog.application.usecase.user;

import com.example.autolog.application.mapper.AuthMapper;
import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCurrentUserUseCase {

    private final AuthenticatedUserService authenticatedUserService;
    private final AuthMapper authMapper;

    public UserResponse execute() {
        UserEntity currentUser = authenticatedUserService.getCurrentUser();
        return authMapper.toUserResponse(currentUser);
    }
}