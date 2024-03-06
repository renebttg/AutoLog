package com.example.autolog.controllers;

import com.example.autolog.dtos.CarRecordDto;
import com.example.autolog.models.CarModel;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.CarRepository;
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
public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/users/{userId}/cars")
    public ResponseEntity<CarModel> saveCar(@PathVariable Long userId, @RequestBody @Valid CarRecordDto carRecordDto) {
        var carModel = new CarModel();
        BeanUtils.copyProperties(carRecordDto, carModel);

        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserModel user = userOptional.get();
        carModel.setUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(carRepository.save(carModel));
    }

    @GetMapping("/users/{userId}/cars")
    public ResponseEntity<List<CarModel>> getAllCarsForUser(@PathVariable Long userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<CarModel> cars = carRepository.findByUser(userOptional.get());

        return ResponseEntity.ok(cars);
    }





}

