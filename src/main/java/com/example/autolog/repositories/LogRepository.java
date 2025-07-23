package com.example.autolog.repositories;

import com.example.autolog.models.LogModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<LogModel, Long> {

    List<LogModel> findByEntity(String entity);
}
