package com.example.autolog.presentation.response.auth;

public record LoginResponse(
        String token,
        UserSummaryResponse user,
        WorkshopSummaryResponse workshop
) {
}
