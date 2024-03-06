package com.example.autolog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRecordDto(@NotBlank String name, @NotBlank String cnpj, @NotBlank String email, @NotNull String phone, @NotNull String nameWorkshop, @NotNull String addressWorkshop) {
}
