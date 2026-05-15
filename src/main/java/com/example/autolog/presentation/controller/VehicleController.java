package com.example.autolog.presentation.controller;

import com.example.autolog.application.usecase.vehicle.CreateVehicleUseCase;
import com.example.autolog.application.usecase.vehicle.DeleteVehicleUseCase;
import com.example.autolog.application.usecase.vehicle.GetVehicleByIdUseCase;
import com.example.autolog.application.usecase.vehicle.ListVehiclesUseCase;
import com.example.autolog.application.usecase.vehicle.UpdateVehicleUseCase;
import com.example.autolog.presentation.request.vehicle.CreateVehicleRequest;
import com.example.autolog.presentation.request.vehicle.UpdateVehicleRequest;
import com.example.autolog.presentation.response.vehicle.VehicleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Rene
 */
@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final CreateVehicleUseCase createVehicleUseCase;
    private final ListVehiclesUseCase listVehiclesUseCase;
    private final GetVehicleByIdUseCase getVehicleByIdUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;

    @PostMapping
    public ResponseEntity<VehicleResponse> create(
            @Valid @RequestBody CreateVehicleRequest request
    ) {
        VehicleResponse response = createVehicleUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> list() {
        List<VehicleResponse> response = listVehiclesUseCase.execute();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getById(
            @PathVariable Long id
    ) {
        VehicleResponse response = getVehicleByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVehicleRequest request
    ) {
        VehicleResponse response = updateVehicleUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        deleteVehicleUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
