package com.example.autolog.controllers;

import com.example.autolog.dtos.EnderecoRecordDTO;
import com.example.autolog.services.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rene
 */

@RestController
public class CepController {

    @Autowired
    private CepService cepService;

    @GetMapping("/buscar-endereco/{cep}")
    public EnderecoRecordDTO buscarEndereco(@PathVariable String cep) {
        return cepService.buscarEnderecoPorCep(cep);
    }

}
