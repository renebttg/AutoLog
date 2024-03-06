package com.example.autolog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceHistoryDTO(@NotBlank String serviceDescription, @NotNull LocalDate repairDate, @NotNull LocalDate lastRepairDate, @NotNull BigDecimal serviceValue) {
}
