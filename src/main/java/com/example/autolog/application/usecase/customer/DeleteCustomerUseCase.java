package com.example.autolog.application.usecase.customer;

import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.CustomerRepository;
import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Transactional
    public void execute(Long customerId) {
        Long workshopId = authenticatedUserService.getCurrentWorkshopId();

        CustomerEntity customer = customerRepository.findByIdAndWorkshopId(customerId, workshopId)
                .orElseThrow(() -> new BusinessException("Customer not found"));

        customerRepository.deleteById(customer.getId());
    }
}