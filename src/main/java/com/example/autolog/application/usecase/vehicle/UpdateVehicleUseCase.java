package com.example.autolog.application.usecase.vehicle;

import com.example.autolog.application.mapper.VehicleMapper;
import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.CustomerRepository;
import com.example.autolog.domain.repository.VehicleRepository;
import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;
import com.example.autolog.infrastructure.persistence.entity.VehicleEntity;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.request.vehicle.UpdateVehicleRequest;
import com.example.autolog.presentation.response.vehicle.VehicleResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Rene
 */
@Service
@RequiredArgsConstructor
public class UpdateVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public VehicleResponse execute(Long vehicleId, UpdateVehicleRequest request) {
        Long workshopId = authenticatedUserService.getCurrentWorkshopId();

        VehicleEntity vehicle = vehicleRepository.findByIdAndWorkshopId(vehicleId, workshopId)
                .orElseThrow(() -> new BusinessException("Vehicle not found"));

        CustomerEntity customer = customerRepository.findByIdAndWorkshopId(request.customerId(), workshopId)
                .orElseThrow(() -> new BusinessException("Customer not found"));

        String licencePlate = normalizeUppercase(request.licencePlate());
        String chassisNumber = normalizeUppercase(request.chassisNumber());

        vehicleRepository.findByLicencePlateAndWorkshopId(licencePlate, workshopId)
                .filter(existingVehicle -> !existingVehicle.getId().equals(vehicleId))
                .ifPresent(existingVehicle -> {
                    throw new BusinessException("A vehicle with this licence plate already exists");
                });

        vehicleRepository.findByChassisNumberAndWorkshopId(chassisNumber, workshopId)
                .filter(existingVehicle -> !existingVehicle.getId().equals(vehicleId))
                .ifPresent(existingVehicle -> {
                    throw new BusinessException("A vehicle with this chassis number already exists");
                });

        vehicleMapper.updateEntity(vehicle, request, customer);

        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    private String normalizeUppercase(String value) {
        return value.trim().toUpperCase();
    }

}