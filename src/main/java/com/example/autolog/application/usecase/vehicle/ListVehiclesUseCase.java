package com.example.autolog.application.usecase.vehicle;

import com.example.autolog.application.mapper.VehicleMapper;
import com.example.autolog.domain.repository.VehicleRepository;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.response.vehicle.VehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rene
 */
@Service
@RequiredArgsConstructor
public class ListVehiclesUseCase {

    private final VehicleRepository vehicleRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final VehicleMapper vehicleMapper;

    public List<VehicleResponse> execute() {
        Long workshopId = authenticatedUserService.getCurrentWorkshopId();

        return vehicleRepository.findAllByWorkshopId(workshopId)
                .stream()
                .map(vehicleMapper::toResponse)
                .toList();
    }
}