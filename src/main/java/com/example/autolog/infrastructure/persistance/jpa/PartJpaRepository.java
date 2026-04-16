package com.example.autolog.infrastructure.persistance.jpa;

import com.example.autolog.infrastructure.persistance.entity.PartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartJpaRepository extends JpaRepository<PartEntity, Long> {

    Optional<PartEntity> findByPartNumber(String partNumber);
    List<PartEntity> findByNameContainingIgnoreCase(String name);
    List<PartEntity> findByQuantityLessThanEqual(Integer quantity);
    List<PartEntity> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    boolean existsByPartNumber(String partNumber);
}
