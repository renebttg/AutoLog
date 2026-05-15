package com.example.autolog.presentation.request.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateVehicleRequest(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @NotBlank(message = "Brand is required")
        @Size(max = 80, message = "Brand must have at most 80 characters")
        String brand,

        @NotBlank(message = "Model is required")
        @Size(max = 80, message = "Model must have at most 80 characters")
        String model,

        @Size(max = 40, message = "Color must have at most 40 characters")
        String color,

        @NotBlank(message = "Licence plate is required")
        @Size(max = 10, message = "Licence plate must have at most 10 characters")
        String licencePlate,

        @NotBlank(message = "Chassis number is required")
        @Size(max = 30, message = "Chassis number must have at most 30 characters")
        String chassisNumber,

        Integer manufactureYear
) {
}