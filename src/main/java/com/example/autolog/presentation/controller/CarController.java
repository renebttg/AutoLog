package com.example.autolog.presentation.controller;

import com.example.autolog.presentation.request.SaveVehicleRequest;
import com.example.autolog.infrastructure.persistence.entity.VehicleEntity;
import com.example.autolog.application.usecase.vehicle.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rene
 */

@RestController
@RequestMapping("/users/{userId}/cars")
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping
    public ResponseEntity<Object> saveCar(@PathVariable Long userId, @RequestBody @Valid SaveVehicleRequest carRecordDto) {
        return carService.saveCar(userId, carRecordDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllCarsForUser(@PathVariable Long userId) {
        return carService.getAllCarsForUser(userId);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<VehicleEntity> getCarByUserIdAndCarId(@PathVariable Long userId, @PathVariable Long carId) {
        return carService.getCarByUserIdAndCarId(userId, carId);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<Object> updateCar(@PathVariable Long userId, @PathVariable Long carId, @RequestBody @Valid SaveVehicleRequest carRecordDto) {
        return carService.updateCar(userId, carId, carRecordDto);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Object> deleteCar(@PathVariable Long userId, @PathVariable Long carId) {
        return carService.deleteCar(userId, carId);
    }

}

