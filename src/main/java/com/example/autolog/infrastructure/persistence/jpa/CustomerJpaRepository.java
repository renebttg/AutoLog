package com.example.autolog.infrastructure.persistence.jpa;

import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByIdAndWorkshopId(Long id, Long workshopId);

    List<CustomerEntity> findAllByWorkshopId(Long workshopId);

    List<CustomerEntity> findByNameContainingIgnoreCaseAndWorkshopId(String name, Long workshopId);
}