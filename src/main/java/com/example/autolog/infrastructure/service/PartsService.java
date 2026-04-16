package com.example.autolog.infrastructure.service;

import com.example.autolog.presentation.request.SavePartRequest;
import com.example.autolog.domain.exception.PartAlreadyExistsException;
import com.example.autolog.domain.exception.PartNotFoundException;
import com.example.autolog.infrastructure.persistance.entity.PartEntity;
import com.example.autolog.domain.repository.PartsRepository;
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
public class PartsService {

    @Autowired
    PartsRepository partsRepository;


    public ResponseEntity<Object> savePart(@RequestBody @Valid SavePartRequest partsRecordDTO) {

        partsRepository.findByPartNumber(partsRecordDTO.partNumber())
                .ifPresent(part -> {
                    throw new PartAlreadyExistsException("Part Number " + partsRecordDTO.partNumber() + " already exists.");
                });

        PartEntity partsModel = new PartEntity();
        BeanUtils.copyProperties(partsRecordDTO, partsModel);

        PartEntity savedPart = partsRepository.save(partsModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPart);
    }


    public ResponseEntity<List<PartEntity>> getAllParts() {
        List<PartEntity> parts = partsRepository.findAll();
        return ResponseEntity.ok(parts);
    }


    public ResponseEntity<Object> getPartById(@PathVariable Long id) {
        PartEntity part = partsRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException("Part with ID " + id + " Not found"));

        return ResponseEntity.ok(part);
    }


    public ResponseEntity<Object> updatePart(@PathVariable Long id, @RequestBody @Valid SavePartRequest partsRecordDTO) {
        PartEntity existingPart = partsRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException("Part with ID " + id + " Not found"));

        BeanUtils.copyProperties(partsRecordDTO, existingPart, "idPart");

        PartEntity updatedPart = partsRepository.save(existingPart);

        return ResponseEntity.ok(updatedPart);
    }


    public ResponseEntity<Object> deletePart(@PathVariable Long id) {
        PartEntity part = partsRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException("Part with ID " + id + " Not found"));

        partsRepository.delete(part);
        return ResponseEntity.ok("Part deleted successfully.");
    }

}
