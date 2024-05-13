package com.example.autolog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRecordDTO(@NotBlank String name, @NotBlank String cnpj, @NotBlank String email, @NotBlank String password, @NotNull String phone, @NotNull String nameWorkshop, @NotNull String addressWorkshop) {
}
