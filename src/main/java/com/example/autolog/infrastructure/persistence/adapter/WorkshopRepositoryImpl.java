package com.example.autolog.infrastructure.persistence.adapter;

import com.example.autolog.domain.repository.WorkshopRepository;
import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;
import com.example.autolog.infrastructure.persistence.jpa.WorkshopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WorkshopRepositoryImpl implements WorkshopRepository {

    private final WorkshopJpaRepository workshopJpaRepository;

    @Override
    public WorkshopEntity save(WorkshopEntity workshop) {
        return workshopJpaRepository.save(workshop);
    }

    @Override
    public Optional<WorkshopEntity> findById(Long id) {
        return workshopJpaRepository.findById(id);
    }

    @Override
    public Optional<WorkshopEntity> findByCnpj(String cnpj) {
        return workshopJpaRepository.findByCnpj(cnpj);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return workshopJpaRepository.existsByCnpj(cnpj);
    }

    @Override
    public List<WorkshopEntity> findAll() {
        return workshopJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        workshopJpaRepository.deleteById(id);
    }
}