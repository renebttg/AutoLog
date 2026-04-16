package com.example.autolog.infrastructure.persistance.jpa;

import com.example.autolog.infrastructure.persistance.entity.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceJpaRepository extends JpaRepository<MaintenanceEntity, Long> {
}
