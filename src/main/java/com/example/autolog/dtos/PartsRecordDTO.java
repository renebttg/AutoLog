package com.example.autolog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PartsRecordDTO(@NotBlank String partNumber, @NotBlank String name, @NotNull Integer quantity, @NotNull BigDecimal price, String description) {
}
