package com.example.autolog.repositories;

import com.example.autolog.models.PartsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartsRepository extends JpaRepository<PartsModel, Long> {

    Optional<PartsModel> findByPartNumber(String partNumber);
    List<PartsModel> findByNameContainingIgnoreCase(String name);
    List<PartsModel> findByQuantityLessThanEqual(Integer quantity);
    List<PartsModel> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    boolean existsByPartNumber(String partNumber);
}
