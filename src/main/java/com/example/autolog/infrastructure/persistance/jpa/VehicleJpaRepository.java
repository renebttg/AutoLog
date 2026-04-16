package com.example.autolog.infrastructure.persistance.jpa;

import com.example.autolog.infrastructure.persistance.entity.VehicleEntity;
import com.example.autolog.infrastructure.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VehicleJpaRepository extends JpaRepository<VehicleEntity, Long> {
    @Query("SELECT c FROM CarModel c WHERE c.user = :user")
    List<VehicleEntity> findByUser(UserEntity user);
    @Query("SELECT c FROM CarModel c WHERE c.user = :user AND c.idCar = :carId")
    Optional<VehicleEntity> findByUserAndIdCar(@Param("user") UserEntity user, @Param("carId") Long carId);
}
