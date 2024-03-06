package com.example.autolog.repositories;

import com.example.autolog.models.CarModel;
import com.example.autolog.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CarRepository extends JpaRepository<CarModel, Long> {
    @Query("SELECT c FROM CarModel c WHERE c.user = :user")
    List<CarModel> findByUser(UserModel user);
    @Query("SELECT c FROM CarModel c WHERE c.user = :user AND c.idCar = :carId")
    Optional<CarModel> findByUserAndIdCar(@Param("user") UserModel user, @Param("carId") Long carId);
}
