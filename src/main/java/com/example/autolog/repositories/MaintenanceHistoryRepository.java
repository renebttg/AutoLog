package com.example.autolog.repositories;

import com.example.autolog.models.MaintenanceHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceHistoryRepository extends JpaRepository<MaintenanceHistoryModel, Long> {
}
