package com.example.autolog.infrastructure.persistence.jpa;

import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndActiveTrue(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findAllByWorkshopId(Long workshopId);
}