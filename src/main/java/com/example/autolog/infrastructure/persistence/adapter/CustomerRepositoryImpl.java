package com.example.autolog.infrastructure.persistence.adapter;

import com.example.autolog.domain.repository.CustomerRepository;
import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;
import com.example.autolog.infrastructure.persistence.jpa.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public CustomerEntity save(CustomerEntity customer) {
        return customerJpaRepository.save(customer);
    }

    @Override
    public Optional<CustomerEntity> findById(Long id) {
        return customerJpaRepository.findById(id);
    }

    @Override
    public Optional<CustomerEntity> findByIdAndWorkshopId(Long id, Long workshopId) {
        return customerJpaRepository.findByIdAndWorkshopId(id, workshopId);
    }

    @Override
    public List<CustomerEntity> findAllByWorkshopId(Long workshopId) {
        return customerJpaRepository.findAllByWorkshopId(workshopId);
    }

    @Override
    public List<CustomerEntity> findByNameContainingIgnoreCaseAndWorkshopId(String name, Long workshopId) {
        return customerJpaRepository.findByNameContainingIgnoreCaseAndWorkshopId(name, workshopId);
    }

    @Override
    public void deleteById(Long id) {
        customerJpaRepository.deleteById(id);
    }
}