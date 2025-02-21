package com.example.autolog.controllers;

import com.example.autolog.dtos.PartsRecordDTO;
import com.example.autolog.models.PartsModel;
import com.example.autolog.services.PartsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Rene
 */
@RestController
@RequestMapping("parts")
public class PartsController {

    @Autowired
    PartsService partsService;

    @PostMapping
    public ResponseEntity<Object> savePart(@RequestBody @Valid PartsRecordDTO partsRecordDTO) {
        return partsService.savePart(partsRecordDTO);
    }

    @GetMapping
    public ResponseEntity<List<PartsModel>> getAllParts() {
        return partsService.getAllParts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPartById(@PathVariable Long id) {
        return partsService.getPartById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePart(@PathVariable Long id, @RequestBody @Valid PartsRecordDTO partsRecordDTO) {
        return partsService.updatePart(id, partsRecordDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePart(@PathVariable Long id) {
        return partsService.deletePart(id);
    }
}
