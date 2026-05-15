package com.example.autolog.infrastructure.security.service;

import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

    public UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public Long getCurrentWorkshopId() {
        return getCurrentUser().getWorkshop().getId();
    }
}
