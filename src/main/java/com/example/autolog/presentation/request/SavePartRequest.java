package com.example.autolog.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SavePartRequest(@NotBlank String partNumber, @NotBlank String name, @NotNull Integer quantity, @NotNull BigDecimal price, String description) {
}
