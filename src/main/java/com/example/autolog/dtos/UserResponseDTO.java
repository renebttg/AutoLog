package com.example.autolog.dtos;

import com.example.autolog.enums.UserRole;
import com.example.autolog.models.UserModel;

public record UserResponseDTO(long idUser, String name, String cnpj, String email, String phone, String nameWorkshop, String addressWorkshop, UserRole role) {

    public static UserResponseDTO fromUserModel(UserModel user) {
        return new UserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getCnpj(),
                user.getEmail(),
                user.getPhone(),
                user.getNameWorkshop(),
                user.getAddressWorkshop(),
                user.getRole()
        );
    }
}
