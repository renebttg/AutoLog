package com.example.autolog.application.usecase.customer;

import com.example.autolog.application.mapper.CustomerMapper;
import com.example.autolog.domain.repository.CustomerRepository;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.response.customer.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCustomersUseCase {

    private final CustomerRepository customerRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final CustomerMapper customerMapper;

    public List<CustomerResponse> execute() {
        Long workshopId = authenticatedUserService.getCurrentWorkshopId();

        return customerRepository.findAllByWorkshopId(workshopId)
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }
}
