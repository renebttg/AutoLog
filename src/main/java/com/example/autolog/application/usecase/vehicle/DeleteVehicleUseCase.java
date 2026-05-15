package com.example.autolog.application.usecase.vehicle;

import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.VehicleRepository;
import com.example.autolog.infrastructure.persistence.entity.VehicleEntity;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Rene
 */
@Service
@RequiredArgsConstructor
public class DeleteVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Transactional
    public void execute(Long vehicleId) {
        Long workshopId = authenticatedUserService.getCurrentWorkshopId();

        VehicleEntity vehicle = vehicleRepository.findByIdAndWorkshopId(vehicleId, workshopId)
                .orElseThrow(() -> new BusinessException("Vehicle not found"));

        vehicleRepository.deleteById(vehicle.getId());
    }
}
