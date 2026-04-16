package com.example.autolog.domain.repository;

import com.example.autolog.infrastructure.persistance.entity.LogModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<LogModel, Long> {

    List<LogModel> findByEntity(String entity);
}
