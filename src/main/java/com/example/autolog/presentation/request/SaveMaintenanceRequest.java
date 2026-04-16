package com.example.autolog.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaveMaintenanceRequest(@NotBlank String serviceDescription, @NotNull String serviceStatus, @NotNull LocalDate repairDate, @NotNull BigDecimal serviceValue) {
}
