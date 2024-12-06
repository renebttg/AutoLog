package com.example.autolog.controllers;

import com.example.autolog.dtos.PartsRecordDTO;
import com.example.autolog.models.PartsModel;
import com.example.autolog.repositories.PartsRepository;
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
@RequestMapping("/parts")
public class PartsController {

    @Autowired
    private PartsRepository partsRepository;


    @PostMapping
    public ResponseEntity<Object> savePart(@RequestBody @Valid PartsRecordDTO partsRecordDTO) {
        if (partsRepository.existsByPartNumber(partsRecordDTO.partNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Part Number already exists.");
        }

        PartsModel partsModel = new PartsModel();
        BeanUtils.copyProperties(partsRecordDTO, partsModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(partsRepository.save(partsModel));
    }


    @GetMapping
    public ResponseEntity<List<PartsModel>> getAllParts() {
        List<PartsModel> parts = partsRepository.findAll();
        return ResponseEntity.ok(parts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getPartById(@PathVariable Long id) {
        Optional<PartsModel> partOptional = partsRepository.findById(id);
        if (partOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Part not found.");
        }
        return ResponseEntity.ok(partOptional.get());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePart(@PathVariable Long id, @RequestBody @Valid PartsRecordDTO partsRecordDTO) {
        Optional<PartsModel> partOptional = partsRepository.findById(id);
        if (partOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Part not found.");
        }

        PartsModel existingPart = partOptional.get();

        BeanUtils.copyProperties(partsRecordDTO, existingPart, "idPart");

        return ResponseEntity.ok(partsRepository.save(existingPart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePart(@PathVariable Long id) {
        Optional<PartsModel> partOptional = partsRepository.findById(id);
        if (partOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Part not found.");
        }

        partsRepository.delete(partOptional.get());
        return ResponseEntity.ok("Part deleted successfully.");
    }
}
