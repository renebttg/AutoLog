package com.example.autolog.application.usecase.customer;

import com.example.autolog.application.mapper.CustomerMapper;
import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.CustomerRepository;
import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.request.customer.UpdateCustomerRequest;
import com.example.autolog.presentation.response.customer.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerResponse execute(Long customerId, UpdateCustomerRequest request) {
        Long workshopId = authenticatedUserService.getCurrentWorkshopId();

        CustomerEntity customer = customerRepository.findByIdAndWorkshopId(customerId, workshopId)
                .orElseThrow(() -> new BusinessException("Customer not found"));

        customerMapper.updateEntity(customer, request);

        CustomerEntity updatedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(updatedCustomer);
    }
}