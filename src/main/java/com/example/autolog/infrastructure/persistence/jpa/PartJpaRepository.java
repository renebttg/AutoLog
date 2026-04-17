package com.example.autolog.infrastructure.persistence.jpa;

import com.example.autolog.infrastructure.persistence.entity.PartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PartJpaRepository extends JpaRepository<PartEntity, Long> {

    Optional<PartEntity> findByPartNumberAndWorkshopId(String partNumber, Long workshopId);

    List<PartEntity> findAllByWorkshopId(Long workshopId);

    List<PartEntity> findByNameContainingIgnoreCaseAndWorkshopId(String name, Long workshopId);

    List<PartEntity> findByQuantityLessThanEqualAndWorkshopId(Integer quantity, Long workshopId);

    List<PartEntity> findByPriceBetweenAndWorkshopId(BigDecimal minPrice, BigDecimal maxPrice, Long workshopId);

    boolean existsByPartNumberAndWorkshopId(String partNumber, Long workshopId);
}