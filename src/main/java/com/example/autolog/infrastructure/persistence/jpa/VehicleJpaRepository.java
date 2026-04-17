package com.example.autolog.infrastructure.persistence.jpa;

import com.example.autolog.infrastructure.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleJpaRepository extends JpaRepository<VehicleEntity, Long> {

    List<VehicleEntity> findAllByWorkshopId(Long workshopId);

    List<VehicleEntity> findAllByCustomerId(Long customerId);

    Optional<VehicleEntity> findByIdAndWorkshopId(Long id, Long workshopId);

    Optional<VehicleEntity> findByLicencePlateAndWorkshopId(String licencePlate, Long workshopId);

    Optional<VehicleEntity> findByChassisNumberAndWorkshopId(String chassisNumber, Long workshopId);

    boolean existsByLicencePlateAndWorkshopId(String licencePlate, Long workshopId);
}