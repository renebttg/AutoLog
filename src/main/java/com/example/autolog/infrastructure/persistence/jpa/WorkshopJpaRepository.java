package com.example.autolog.infrastructure.persistence.jpa;

import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkshopJpaRepository extends JpaRepository<WorkshopEntity, Long> {

    Optional<WorkshopEntity> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);
}