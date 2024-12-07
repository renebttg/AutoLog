package com.example.autolog.services;

import com.example.autolog.dtos.EnderecoRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Rene
 */
@Service
public class CepService {

    @Autowired
    private RestTemplate restTemplate;

    public EnderecoRecordDTO buscarEnderecoPorCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, EnderecoRecordDTO.class);
    }
}
