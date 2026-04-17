package com.example.autolog.domain.repository;

import com.example.autolog.infrastructure.persistence.entity.VehicleEntity;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    VehicleEntity save(VehicleEntity vehicle);

    Optional<VehicleEntity> findById(Long id);

    Optional<VehicleEntity> findByIdAndWorkshopId(Long id, Long workshopId);

    Optional<VehicleEntity> findByLicencePlateAndWorkshopId(String licencePlate, Long workshopId);

    Optional<VehicleEntity> findByChassisNumberAndWorkshopId(String chassisNumber, Long workshopId);

    boolean existsByLicencePlateAndWorkshopId(String licencePlate, Long workshopId);

    List<VehicleEntity> findAllByWorkshopId(Long workshopId);

    List<VehicleEntity> findAllByCustomerId(Long customerId);

    void deleteById(Long id);
}