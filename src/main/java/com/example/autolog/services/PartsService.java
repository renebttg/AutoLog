package com.example.autolog.services;

import com.example.autolog.dtos.PartsRecordDTO;
import com.example.autolog.exceptions.PartAlreadyExistsException;
import com.example.autolog.exceptions.PartNotFoundException;
import com.example.autolog.models.PartsModel;
import com.example.autolog.repositories.PartsRepository;
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


    public ResponseEntity<Object> savePart(@RequestBody @Valid PartsRecordDTO partsRecordDTO) {

        partsRepository.findByPartNumber(partsRecordDTO.partNumber())
                .ifPresent(part -> {
                    throw new PartAlreadyExistsException("Part Number " + partsRecordDTO.partNumber() + " already exists.");
                });

        PartsModel partsModel = new PartsModel();
        BeanUtils.copyProperties(partsRecordDTO, partsModel);

        PartsModel savedPart = partsRepository.save(partsModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPart);
    }


    public ResponseEntity<List<PartsModel>> getAllParts() {
        List<PartsModel> parts = partsRepository.findAll();
        return ResponseEntity.ok(parts);
    }


    public ResponseEntity<Object> getPartById(@PathVariable Long id) {
        PartsModel part = partsRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException("Part with ID" + id + "Not found"));

        return ResponseEntity.ok(part);
    }


    public ResponseEntity<Object> updatePart(@PathVariable Long id, @RequestBody @Valid PartsRecordDTO partsRecordDTO) {
        PartsModel existingPart = partsRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException("Part with ID" + id + "Not found"));

        BeanUtils.copyProperties(partsRecordDTO, existingPart, "idPart");

        PartsModel updatedPart = partsRepository.save(existingPart);

        return ResponseEntity.ok(updatedPart);
    }


    public ResponseEntity<Object> deletePart(@PathVariable Long id) {
        PartsModel part = partsRepository.findById(id)
                .orElseThrow(() -> new PartNotFoundException("Part with ID" + id + "Not found"));

        partsRepository.delete(part);
        return ResponseEntity.ok("Part deleted successfully.");
    }

}
