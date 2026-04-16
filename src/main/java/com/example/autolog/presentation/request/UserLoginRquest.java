package com.example.autolog.presentation.request;

import jakarta.validation.constraints.NotNull;

public record UserLoginRquest(@NotNull String email, @NotNull String password) {
}
