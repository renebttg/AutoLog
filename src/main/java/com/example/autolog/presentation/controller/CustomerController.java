package com.example.autolog.presentation.controller;

import com.example.autolog.application.usecase.customer.CreateCustomerUseCase;
import com.example.autolog.application.usecase.customer.DeleteCustomerUseCase;
import com.example.autolog.application.usecase.customer.GetCustomerByIdUseCase;
import com.example.autolog.application.usecase.customer.ListCustomersUseCase;
import com.example.autolog.application.usecase.customer.UpdateCustomerUseCase;
import com.example.autolog.presentation.request.customer.CreateCustomerRequest;
import com.example.autolog.presentation.request.customer.UpdateCustomerRequest;
import com.example.autolog.presentation.response.customer.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final ListCustomersUseCase listCustomersUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(
            @Valid @RequestBody CreateCustomerRequest request
    ) {
        CustomerResponse response = createCustomerUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> list() {
        List<CustomerResponse> response = listCustomersUseCase.execute();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(
            @PathVariable Long id
    ) {
        CustomerResponse response = getCustomerByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request
    ) {
        CustomerResponse response = updateCustomerUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
