package com.example.autolog.domain.repository;

import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    CustomerEntity save(CustomerEntity customer);

    Optional<CustomerEntity> findById(Long id);

    Optional<CustomerEntity> findByIdAndWorkshopId(Long id, Long workshopId);

    List<CustomerEntity> findAllByWorkshopId(Long workshopId);

    List<CustomerEntity> findByNameContainingIgnoreCaseAndWorkshopId(String name, Long workshopId);

    void deleteById(Long id);
}