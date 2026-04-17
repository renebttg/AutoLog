package com.example.autolog.domain.repository;

import com.example.autolog.infrastructure.persistence.entity.PartEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PartRepository {

    PartEntity save(PartEntity part);

    Optional<PartEntity> findById(Long id);

    Optional<PartEntity> findByPartNumberAndWorkshopId(String partNumber, Long workshopId);

    List<PartEntity> findAllByWorkshopId(Long workshopId);

    List<PartEntity> findByNameContainingIgnoreCaseAndWorkshopId(String name, Long workshopId);

    List<PartEntity> findByQuantityLessThanEqualAndWorkshopId(Integer quantity, Long workshopId);

    List<PartEntity> findByPriceBetweenAndWorkshopId(BigDecimal minPrice, BigDecimal maxPrice, Long workshopId);

    boolean existsByPartNumberAndWorkshopId(String partNumber, Long workshopId);

    void deleteById(Long id);
}