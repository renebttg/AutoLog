package com.example.autolog.domain.repository;

import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;

import java.util.List;
import java.util.Optional;

public interface WorkshopRepository {

    WorkshopEntity save(WorkshopEntity workshop);

    Optional<WorkshopEntity> findById(Long id);

    Optional<WorkshopEntity> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

    List<WorkshopEntity> findAll();

    void deleteById(Long id);
}
