package com.example.autolog.infrastructure.persistence.adapter;

import com.example.autolog.domain.repository.MaintenanceRepository;
import com.example.autolog.infrastructure.persistence.entity.MaintenanceEntity;
import com.example.autolog.infrastructure.persistence.jpa.MaintenanceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MaintenanceRepositoryImpl implements MaintenanceRepository {

    private final MaintenanceJpaRepository maintenanceJpaRepository;

    @Override
    public MaintenanceEntity save(MaintenanceEntity maintenance) {
        return maintenanceJpaRepository.save(maintenance);
    }

    @Override
    public Optional<MaintenanceEntity> findById(Long id) {
        return maintenanceJpaRepository.findById(id);
    }

    @Override
    public List<MaintenanceEntity> findAllByVehicleId(Long vehicleId) {
        return maintenanceJpaRepository.findAllByVehicleId(vehicleId);
    }

    @Override
    public List<MaintenanceEntity> findAllByVehicleWorkshopId(Long workshopId) {
        return maintenanceJpaRepository.findAllByVehicleWorkshopId(workshopId);
    }

    @Override
    public void deleteById(Long id) {
        maintenanceJpaRepository.deleteById(id);
    }
}