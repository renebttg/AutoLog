package com.example.autolog.dtos;

import jakarta.validation.constraints.NotBlank;
public record CarRecordDTO(@NotBlank String ownerName , @NotBlank String carBrand, @NotBlank String model, @NotBlank String color, @NotBlank String licencePlate, @NotBlank String chassisNumber) {
}
