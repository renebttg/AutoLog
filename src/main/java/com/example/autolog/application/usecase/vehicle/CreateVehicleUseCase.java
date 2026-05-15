package com.example.autolog.application.usecase.vehicle;

import com.example.autolog.application.mapper.VehicleMapper;
import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.CustomerRepository;
import com.example.autolog.domain.repository.VehicleRepository;
import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;
import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import com.example.autolog.infrastructure.persistence.entity.VehicleEntity;
import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.request.vehicle.CreateVehicleRequest;
import com.example.autolog.presentation.response.vehicle.VehicleResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Rene
 */

@Service
@RequiredArgsConstructor
public class CreateVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public VehicleResponse execute(CreateVehicleRequest request) {
        UserEntity currentUser = authenticatedUserService.getCurrentUser();
        WorkshopEntity workshop = currentUser.getWorkshop();
        Long workshopId = workshop.getId();

        CustomerEntity customer = customerRepository.findByIdAndWorkshopId(request.customerId(), workshopId)
                .orElseThrow(() -> new BusinessException("Customer not found"));

        if (vehicleRepository.existsByLicencePlateAndWorkshopId(normalizeUppercase(request.licencePlate()), workshopId)) {
            throw new BusinessException("A vehicle with this licence plate already exists");
        }

        vehicleRepository.findByChassisNumberAndWorkshopId(normalizeUppercase(request.chassisNumber()), workshopId)
                .ifPresent(vehicle -> {
                    throw new BusinessException("A vehicle with this chassis number already exists");
                });

        VehicleEntity vehicle = vehicleMapper.toEntity(request, workshop, customer);
        VehicleEntity savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toResponse(savedVehicle);
    }

    private String normalizeUppercase(String value) {
        return value.trim().toUpperCase();
    }
}
