package com.example.autolog.infrastructure.persistence.adapter;

import com.example.autolog.domain.repository.UserRepository;
import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import com.example.autolog.infrastructure.persistence.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserEntity save(UserEntity user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByEmailAndActiveTrue(String email) {
        return userJpaRepository.findByEmailAndActiveTrue(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public List<UserEntity> findAllByWorkshopId(Long workshopId) {
        return userJpaRepository.findAllByWorkshopId(workshopId);
    }

    @Override
    public List<UserEntity> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }
}