package com.example.autolog.infrastructure.persistence.adapter;

import com.example.autolog.domain.repository.VehicleRepository;
import com.example.autolog.infrastructure.persistence.entity.VehicleEntity;
import com.example.autolog.infrastructure.persistence.jpa.VehicleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleRepositoryImpl implements VehicleRepository {

    private final VehicleJpaRepository vehicleJpaRepository;

    @Override
    public VehicleEntity save(VehicleEntity vehicle) {
        return vehicleJpaRepository.save(vehicle);
    }

    @Override
    public Optional<VehicleEntity> findById(Long id) {
        return vehicleJpaRepository.findById(id);
    }

    @Override
    public Optional<VehicleEntity> findByIdAndWorkshopId(Long id, Long workshopId) {
        return vehicleJpaRepository.findByIdAndWorkshopId(id, workshopId);
    }

    @Override
    public Optional<VehicleEntity> findByLicencePlateAndWorkshopId(String licencePlate, Long workshopId) {
        return vehicleJpaRepository.findByLicencePlateAndWorkshopId(licencePlate, workshopId);
    }

    @Override
    public Optional<VehicleEntity> findByChassisNumberAndWorkshopId(String chassisNumber, Long workshopId) {
        return vehicleJpaRepository.findByChassisNumberAndWorkshopId(chassisNumber, workshopId);
    }

    @Override
    public boolean existsByLicencePlateAndWorkshopId(String licencePlate, Long workshopId) {
        return vehicleJpaRepository.existsByLicencePlateAndWorkshopId(licencePlate, workshopId);
    }

    @Override
    public List<VehicleEntity> findAllByWorkshopId(Long workshopId) {
        return vehicleJpaRepository.findAllByWorkshopId(workshopId);
    }

    @Override
    public List<VehicleEntity> findAllByCustomerId(Long customerId) {
        return vehicleJpaRepository.findAllByCustomerId(customerId);
    }

    @Override
    public void deleteById(Long id) {
        vehicleJpaRepository.deleteById(id);
    }
}