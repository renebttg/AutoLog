package com.example.autolog.domain.repository;

import com.example.autolog.infrastructure.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    UserEntity save(UserEntity user);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndActiveTrue(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findAllByWorkshopId(Long workshopId);

    List<UserEntity> findAll();

    void deleteById(Long id);
}