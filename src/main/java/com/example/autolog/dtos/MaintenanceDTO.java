package com.example.autolog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceDTO(@NotBlank String serviceDescription, @NotNull String serviceStatus, @NotNull LocalDate repairDate, @NotNull BigDecimal serviceValue) {
}
