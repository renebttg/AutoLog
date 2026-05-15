package com.example.autolog.application.usecase.vehicle;

import com.example.autolog.application.mapper.VehicleMapper;
import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.VehicleRepository;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.response.vehicle.VehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Rene
 */
@Service
@RequiredArgsConstructor
public class GetVehicleByIdUseCase {

    private final VehicleRepository vehicleRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final VehicleMapper vehicleMapper;

    public VehicleResponse execute(Long vehicleId) {
        Long workshopId = authenticatedUserService.getCurrentWorkshopId();

        return vehicleRepository.findByIdAndWorkshopId(vehicleId, workshopId)
                .map(vehicleMapper::toResponse)
                .orElseThrow(() -> new BusinessException("Vehicle not found"));
    }
}
