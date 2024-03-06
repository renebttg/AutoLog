package com.example.autolog.repositories;

import com.example.autolog.models.CarModel;
import com.example.autolog.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface CarRepository extends JpaRepository<CarModel, Long> {
    @Query("SELECT c FROM CarModel c WHERE c.user = :user")
    List<CarModel> findByUser(UserModel user);

}
