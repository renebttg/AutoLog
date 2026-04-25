package com.example.autolog.application.mapper;

import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;
import com.example.autolog.presentation.response.auth.UserSummaryResponse;
import com.example.autolog.presentation.response.auth.WorkshopSummaryResponse;
import com.example.autolog.presentation.response.user.UserResponse;
import com.example.autolog.presentation.response.workshop.WorkshopResponse;
import org.springframework.stereotype.Component;

/**
 * @author Rene
 */
@Component
public class AuthMapper {

    public WorkshopResponse toWorkshopResponse(WorkshopEntity workshop) {
        return new WorkshopResponse(
                workshop.getId(),
                workshop.getName(),
                workshop.getCnpj(),
                workshop.getPhone(),
                workshop.getAddress(),
                workshop.getActive()
        );
    }

    public WorkshopSummaryResponse toWorkshopSummaryResponse(WorkshopEntity workshop) {
        return new WorkshopSummaryResponse(
                workshop.getId(),
                workshop.getName(),
                workshop.getCnpj()
        );
    }

    public UserResponse toUserResponse(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getActive(),
                user.getWorkshop().getId()
        );
    }

    public UserSummaryResponse toUserSummaryResponse(UserEntity user) {
        return new UserSummaryResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
