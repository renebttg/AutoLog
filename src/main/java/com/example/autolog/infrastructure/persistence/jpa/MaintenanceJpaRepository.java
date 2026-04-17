package com.example.autolog.infrastructure.persistence.jpa;

import com.example.autolog.infrastructure.persistence.entity.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceJpaRepository extends JpaRepository<MaintenanceEntity, Long> {

    List<MaintenanceEntity> findAllByVehicleId(Long vehicleId);

    List<MaintenanceEntity> findAllByVehicleWorkshopId(Long workshopId);
}