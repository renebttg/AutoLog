package com.example.autolog.dtos;

import com.example.autolog.enums.UserRole;

public record UserResponseDTO(long idUser, String name, String cnpj, String email, String phone, String nameWorkshop, String addressWorkshop, UserRole role) {
}
