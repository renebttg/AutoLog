package com.example.autolog.presentation.response;

import com.example.autolog.domain.enums.UserRole;
import com.example.autolog.infrastructure.persistance.entity.UserEntity;

public record WorkshopResponse(long idUser, String name, String cnpj, String email, String phone, String nameWorkshop, String addressWorkshop, UserRole role) {

    public static WorkshopResponse fromUserModel(UserEntity user) {
        return new WorkshopResponse(
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
