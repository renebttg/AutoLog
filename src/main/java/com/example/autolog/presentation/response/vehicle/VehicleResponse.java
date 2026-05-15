package com.example.autolog.presentation.response.vehicle;

public record VehicleResponse(
        Long id,
        Long workshopId,
        Long customerId,
        String customerName,
        String brand,
        String model,
        String color,
        String licencePlate,
        String chassisNumber,
        Integer manufactureYear
) {
}