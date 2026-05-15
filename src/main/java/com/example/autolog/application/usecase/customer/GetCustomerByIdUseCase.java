package com.example.autolog.application.usecase.customer;

import com.example.autolog.application.mapper.CustomerMapper;
import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.CustomerRepository;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.response.customer.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCustomerByIdUseCase {

    private final CustomerRepository customerRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final CustomerMapper customerMapper;

    public CustomerResponse execute(Long customerId) {
        Long workshopId = authenticatedUserService.getCurrentWorkshopId();

        return customerRepository.findByIdAndWorkshopId(customerId, workshopId)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new BusinessException("Customer not found"));
    }
}
