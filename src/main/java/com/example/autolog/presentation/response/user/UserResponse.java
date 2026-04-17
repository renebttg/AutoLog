package com.example.autolog.presentation.response.user;

import com.example.autolog.domain.enums.UserRole;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        UserRole role,
        Boolean active,
        Long workshopId
) {
}
