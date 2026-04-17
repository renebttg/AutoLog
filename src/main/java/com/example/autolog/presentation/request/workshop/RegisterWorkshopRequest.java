package com.example.autolog.presentation.request.workshop;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterWorkshopRequest(
        @NotBlank(message = "Workshop name is required")
        @Size(max = 150, message = "Workshop name must have at most 150 characters")
        String workshopName,

        @NotBlank(message = "CNPJ is required")
        @Size(max = 18, message = "CNPJ must have at most 18 characters")
        String cnpj,

        @Size(max = 20, message = "Phone must have at most 20 characters")
        String phone,

        @Size(max = 255, message = "Address must have at most 255 characters")
        String address,

        @NotBlank(message = "Owner name is required")
        @Size(max = 150, message = "Owner name must have at most 150 characters")
        String ownerName,

        @NotBlank(message = "Owner email is required")
        @Email(message = "Owner email must be valid")
        String ownerEmail,

        @NotBlank(message = "Owner password is required")
        String ownerPassword,

        @Size(max = 20, message = "Owner phone must have at most 20 characters")
        String ownerPhone
) {
}