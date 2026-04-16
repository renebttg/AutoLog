package com.example.autolog.infrastructure.service;

import com.example.autolog.infrastructure.persistance.entity.LogModel;
import com.example.autolog.domain.repository.LogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rene
 */

@Service
public class LogService {

    @Autowired
    LogRepository logRepository;

    public void logRegister(String entity, String action, String user, Object beforeObj, Object afterObject) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            LogModel log = new LogModel();
            log.setEntity(entity);
            log.setAction(action);
            log.setUser(user);
            log.setDateTime(LocalDateTime.now());

            log.setBefore(mapper.writeValueAsString(beforeObj));
            log.setAfter(mapper.writeValueAsString(afterObject));

            logRepository.save(log);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object for logging", e);
        }


    }

    public List<LogModel> getAllLogs() {
        return logRepository.findAll();
    }

    public List<LogModel> getLogsByEntity(String entity) {
        return logRepository.findByEntity(entity);
    }




}
