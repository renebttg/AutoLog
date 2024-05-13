package com.example.autolog.repositories;

import com.example.autolog.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);

    UserModel findByCnpj(String cnpj);
}
