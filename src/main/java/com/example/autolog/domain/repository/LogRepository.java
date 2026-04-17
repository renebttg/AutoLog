package com.example.autolog.domain.repository;

import com.example.autolog.infrastructure.persistence.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<AuditLogEntity, Long> {

    List<AuditLogEntity> findByEntity(String entity);
}
