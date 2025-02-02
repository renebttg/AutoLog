package com.example.autolog.controllers;

import com.example.autolog.dtos.MaintenanceDTO;
import com.example.autolog.exceptions.CarNotFoundException;
import com.example.autolog.exceptions.MaintenanceNotFoundException;
import com.example.autolog.exceptions.UserNotFoundException;
import com.example.autolog.models.CarModel;
import com.example.autolog.models.MaintenanceModel;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.CarRepository;
import com.example.autolog.repositories.MaintenanceRepository;
import com.example.autolog.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Rene
 */
@RestController
public class MaintenanceController {

    @Autowired
    MaintenanceRepository maintenanceRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/users/{userId}/cars/{carId}/maintenance")
    public ResponseEntity<Object> saveMaintenance(@PathVariable Long userId, @PathVariable Long carId, @RequestBody @Valid MaintenanceDTO maintenanceDTO) {
        var maintenanceModel = new MaintenanceModel();
        BeanUtils.copyProperties(maintenanceDTO, maintenanceModel);

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        CarModel car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        maintenanceModel.setCar(car);

        MaintenanceModel savedMaintenance = maintenanceRepository.save(maintenanceModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMaintenance);
    }

    @GetMapping("/users/{userId}/cars/{carId}/maintenance")
    public ResponseEntity<Object> getAllMaintenanceForCar(@PathVariable Long userId, @PathVariable Long carId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        CarModel car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        List<MaintenanceModel> maintenanceList = car.getMaintenanceHistory();
        return ResponseEntity.ok(maintenanceList);
    }

    @GetMapping("/users/{userId}/cars/{carId}/maintenance/{maintenanceId}")
    public ResponseEntity<Object> getMaintenanceForCar(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        CarModel car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        MaintenanceModel maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance with ID " + maintenanceId + " not found"));

        return ResponseEntity.ok(maintenance);
    }

    @PutMapping("/users/{userId}/cars/{carId}/maintenance/{maintenanceId}")
    public ResponseEntity<Object> updateMaintenance(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId, @RequestBody @Valid MaintenanceDTO maintenanceDTO) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        CarModel car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        MaintenanceModel existingMaintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance with ID " + maintenanceId + " not found"));

        BeanUtils.copyProperties(maintenanceDTO, existingMaintenance, "idMaintenance", "car");

        MaintenanceModel updatedMaintenance = maintenanceRepository.save(existingMaintenance);

        return ResponseEntity.ok(updatedMaintenance);
    }

    @DeleteMapping("/users/{userId}/cars/{carId}/maintenance/{maintenanceId}")
    public ResponseEntity<Object> deleteMaintenance(@PathVariable Long userId, @PathVariable Long carId, @PathVariable Long maintenanceId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        CarModel car = carRepository.findByUserAndIdCar(user, carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID " + carId + " not found for user with ID " + userId));

        MaintenanceModel maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new MaintenanceNotFoundException("Maintenance with ID " + maintenanceId + " not found"));

        maintenanceRepository.delete(maintenance);
        return ResponseEntity.ok("Maintenance deleted successfully");
    }







}
