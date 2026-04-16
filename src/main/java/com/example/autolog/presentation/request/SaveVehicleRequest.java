package com.example.autolog.presentation.request;

import jakarta.validation.constraints.NotBlank;
public record SaveVehicleRequest(@NotBlank String ownerName , @NotBlank String carBrand, @NotBlank String model, @NotBlank String color, @NotBlank String licencePlate, @NotBlank String chassisNumber) {
}
