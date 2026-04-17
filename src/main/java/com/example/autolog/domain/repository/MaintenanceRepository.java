package com.example.autolog.domain.repository;

import com.example.autolog.infrastructure.persistence.entity.MaintenanceEntity;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository {

    MaintenanceEntity save(MaintenanceEntity maintenance);

    Optional<MaintenanceEntity> findById(Long id);

    List<MaintenanceEntity> findAllByVehicleId(Long vehicleId);

    List<MaintenanceEntity> findAllByVehicleWorkshopId(Long workshopId);

    void deleteById(Long id);
}
