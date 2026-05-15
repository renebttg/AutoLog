package com.example.autolog.application.mapper;

import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;
import com.example.autolog.infrastructure.persistence.entity.VehicleEntity;
import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;
import com.example.autolog.presentation.request.vehicle.CreateVehicleRequest;
import com.example.autolog.presentation.request.vehicle.UpdateVehicleRequest;
import com.example.autolog.presentation.response.vehicle.VehicleResponse;
import org.springframework.stereotype.Component;

/**
 * @author Rene
 */

@Component
public class VehicleMapper {

    public VehicleEntity toEntity(
            CreateVehicleRequest request,
            WorkshopEntity workshop,
            CustomerEntity customer
    ) {
        return VehicleEntity.builder()
                .workshop(workshop)
                .customer(customer)
                .brand(request.brand().trim())
                .model(request.model().trim())
                .color(normalizeNullable(request.color()))
                .licencePlate(normalizeUppercase(request.licencePlate()))
                .chassisNumber(normalizeUppercase(request.chassisNumber()))
                .manufactureYear(request.manufactureYear())
                .build();
    }

    public void updateEntity(
            VehicleEntity vehicle,
            UpdateVehicleRequest request,
            CustomerEntity customer
    ) {
        vehicle.setCustomer(customer);
        vehicle.setBrand(request.brand().trim());
        vehicle.setModel(request.model().trim());
        vehicle.setColor(normalizeNullable(request.color()));
        vehicle.setLicencePlate(normalizeUppercase(request.licencePlate()));
        vehicle.setChassisNumber(normalizeUppercase(request.chassisNumber()));
        vehicle.setManufactureYear(request.manufactureYear());
    }

    public VehicleResponse toResponse(VehicleEntity vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getWorkshop().getId(),
                vehicle.getCustomer().getId(),
                vehicle.getCustomer().getName(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getColor(),
                vehicle.getLicencePlate(),
                vehicle.getChassisNumber(),
                vehicle.getManufactureYear()
        );
    }

    private String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private String normalizeUppercase(String value) {
        return value.trim().toUpperCase();
    }
}
