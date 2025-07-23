package com.example.autolog.repositories;

import com.example.autolog.models.LogModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogModel, Long> {
}
