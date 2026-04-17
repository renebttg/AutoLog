package com.example.autolog.presentation.controller;

import com.example.autolog.presentation.request.SavePartRequest;
import com.example.autolog.infrastructure.persistence.entity.PartEntity;
import com.example.autolog.application.usecase.part.PartsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rene
 */
@RestController
@RequestMapping("parts")
public class PartsController {

    @Autowired
    PartsService partsService;

    @PostMapping
    public ResponseEntity<Object> savePart(@RequestBody @Valid SavePartRequest partsRecordDTO) {
        return partsService.savePart(partsRecordDTO);
    }

    @GetMapping
    public ResponseEntity<List<PartEntity>> getAllParts() {
        return partsService.getAllParts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPartById(@PathVariable Long id) {
        return partsService.getPartById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePart(@PathVariable Long id, @RequestBody @Valid SavePartRequest partsRecordDTO) {
        return partsService.updatePart(id, partsRecordDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePart(@PathVariable Long id) {
        return partsService.deletePart(id);
    }
}
