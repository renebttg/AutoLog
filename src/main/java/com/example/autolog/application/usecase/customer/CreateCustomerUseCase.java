package com.example.autolog.application.usecase.customer;

import com.example.autolog.application.mapper.CustomerMapper;
import com.example.autolog.domain.repository.CustomerRepository;
import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;
import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.request.customer.CreateCustomerRequest;
import com.example.autolog.presentation.response.customer.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerResponse execute(CreateCustomerRequest request) {
        UserEntity currentUser = authenticatedUserService.getCurrentUser();
        WorkshopEntity workshop = currentUser.getWorkshop();

        CustomerEntity customer = customerMapper.toEntity(request, workshop);
        CustomerEntity savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }
}
