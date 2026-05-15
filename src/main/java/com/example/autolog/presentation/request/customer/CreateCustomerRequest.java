package com.example.autolog.presentation.request.customer;

import com.example.autolog.domain.enums.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(
        @NotBlank(message = "Customer name is required")
        @Size(max = 150)
        String name,

        @NotNull(message = "Customer type is required")
        CustomerType type,

        @Size(max = 20)
        String phone,

        @Email(message = "Email must be valid")
        @Size(max = 150)
        String email,

        @Size(max = 20)
        String document
) {
}