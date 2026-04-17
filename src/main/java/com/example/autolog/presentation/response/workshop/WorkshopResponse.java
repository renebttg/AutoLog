package com.example.autolog.presentation.response.workshop;

public record WorkshopResponse(
        Long id,
        String name,
        String cnpj,
        String phone,
        String address,
        Boolean active
) {
}
