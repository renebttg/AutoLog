package com.example.autolog.infrastructure.persistence.adapter;

import com.example.autolog.domain.repository.PartRepository;
import com.example.autolog.infrastructure.persistence.entity.PartEntity;
import com.example.autolog.infrastructure.persistence.jpa.PartJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PartRepositoryImpl implements PartRepository {

    private final PartJpaRepository partJpaRepository;

    @Override
    public PartEntity save(PartEntity part) {
        return partJpaRepository.save(part);
    }

    @Override
    public Optional<PartEntity> findById(Long id) {
        return partJpaRepository.findById(id);
    }

    @Override
    public Optional<PartEntity> findByPartNumberAndWorkshopId(String partNumber, Long workshopId) {
        return partJpaRepository.findByPartNumberAndWorkshopId(partNumber, workshopId);
    }

    @Override
    public List<PartEntity> findAllByWorkshopId(Long workshopId) {
        return partJpaRepository.findAllByWorkshopId(workshopId);
    }

    @Override
    public List<PartEntity> findByNameContainingIgnoreCaseAndWorkshopId(String name, Long workshopId) {
        return partJpaRepository.findByNameContainingIgnoreCaseAndWorkshopId(name, workshopId);
    }

    @Override
    public List<PartEntity> findByQuantityLessThanEqualAndWorkshopId(Integer quantity, Long workshopId) {
        return partJpaRepository.findByQuantityLessThanEqualAndWorkshopId(quantity, workshopId);
    }

    @Override
    public List<PartEntity> findByPriceBetweenAndWorkshopId(BigDecimal minPrice, BigDecimal maxPrice, Long workshopId) {
        return partJpaRepository.findByPriceBetweenAndWorkshopId(minPrice, maxPrice, workshopId);
    }

    @Override
    public boolean existsByPartNumberAndWorkshopId(String partNumber, Long workshopId) {
        return partJpaRepository.existsByPartNumberAndWorkshopId(partNumber, workshopId);
    }

    @Override
    public void deleteById(Long id) {
        partJpaRepository.deleteById(id);
    }
}