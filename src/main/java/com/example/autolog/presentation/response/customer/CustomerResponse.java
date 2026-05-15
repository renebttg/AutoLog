package com.example.autolog.presentation.response.customer;

import com.example.autolog.domain.enums.CustomerType;

public record CustomerResponse(
        Long id,
        String name,
        CustomerType type,
        String phone,
        String email,
        String document,
        Long workshopId
) {
}