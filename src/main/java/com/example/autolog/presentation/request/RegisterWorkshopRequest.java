package com.example.autolog.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterWorkshopRequest(@NotBlank String name, @NotBlank String cnpj, @NotBlank String email, @NotBlank String password, @NotNull String phone, @NotNull String nameWorkshop, @NotNull String addressWorkshop) {
}
