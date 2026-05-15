package com.example.autolog.application.mapper;

import com.example.autolog.infrastructure.persistence.entity.CustomerEntity;
import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;
import com.example.autolog.presentation.request.customer.CreateCustomerRequest;
import com.example.autolog.presentation.request.customer.UpdateCustomerRequest;
import com.example.autolog.presentation.response.customer.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(CreateCustomerRequest request, WorkshopEntity workshop) {
        return CustomerEntity.builder()
                .workshop(workshop)
                .name(request.name().trim())
                .type(request.type())
                .phone(normalizeNullable(request.phone()))
                .email(normalizeNullableLowercase(request.email()))
                .document(normalizeNullable(request.document()))
                .build();
    }

    public void updateEntity(CustomerEntity customer, UpdateCustomerRequest request) {
        customer.setName(request.name().trim());
        customer.setType(request.type());
        customer.setPhone(normalizeNullable(request.phone()));
        customer.setEmail(normalizeNullableLowercase(request.email()));
        customer.setDocument(normalizeNullable(request.document()));
    }

    public CustomerResponse toResponse(CustomerEntity customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getType(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getDocument(),
                customer.getWorkshop().getId()
        );
    }

    private String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private String normalizeNullableLowercase(String value) {
        return value == null || value.isBlank() ? null : value.trim().toLowerCase();
    }
}
