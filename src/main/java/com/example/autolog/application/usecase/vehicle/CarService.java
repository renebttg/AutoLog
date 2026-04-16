package com.example.autolog.application.usecase.vehicle;

import com.example.autolog.presentation.request.SaveVehicleRequest;
import com.example.autolog.domain.exception.CarNotFoundException;
import com.example.autolog.domain.exception.UserNotFoundException;
import com.example.autolog.infrastructure.persistance.entity.VehicleEntity;
import com.example.autolog.infrastructure.persistance.entity.UserEntity;
import com.example.autolog.infrastructure.persistance.jpa.VehicleJpaRepository;
import com.example.autolog.infrastructure.persistance.jpa.UserJpaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Rene
 */
@Service
public class CarService {

    @Autowired
    VehicleJpaRepository vehicleJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    public ResponseEntity<Object> saveCar(@PathVariable Long userId, @RequestBody @Valid SaveVehicleRequest carRecordDto) {
        var carModel = new VehicleEntity();
        BeanUtils.copyProperties(carRecordDto, carModel);

        UserEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        carModel.setUser(user);

        VehicleEntity savedCar = vehicleJpaRepository.save(carModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    public ResponseEntity<Object> getAllCarsForUser(@PathVariable Long userId) {
        UserEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        List<VehicleEntity> cars = vehicleJpaRepository.findByUser(user);

        return ResponseEntity.ok(cars);
    }

    public ResponseEntity<VehicleEntity> getCarByUserIdAndCarId(@PathVariable Long userId, @PathVariable Long carId) {
        UserEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        VehicleEntity car = vehicleJpaRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        return ResponseEntity.ok(car);
    }

    public ResponseEntity<Object> updateCar(@PathVariable Long userId, @PathVariable Long carId, @RequestBody @Valid SaveVehicleRequest carRecordDto) {
        UserEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        VehicleEntity existingCar = vehicleJpaRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        BeanUtils.copyProperties(carRecordDto, existingCar, "idCar", "user", "maintenanceHistory");

        VehicleEntity updatedCar = vehicleJpaRepository.save(existingCar);

        return ResponseEntity.ok(updatedCar);
    }

    public ResponseEntity<Object> deleteCar(@PathVariable Long userId, @PathVariable Long carId) {
        UserEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        VehicleEntity car = vehicleJpaRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        vehicleJpaRepository.delete(car);
        return ResponseEntity.ok("Car Deleted Successfully");
    }

}
