package com.example.autolog.dtos;

import com.example.autolog.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record UserLoginDTO(@NotNull String email, @NotNull String password) {
}
