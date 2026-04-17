package com.example.autolog.presentation.response.auth;

import com.example.autolog.domain.enums.UserRole;

public record UserSummaryResponse(
        Long id,
        String name,
        String email,
        UserRole role
) {
}
