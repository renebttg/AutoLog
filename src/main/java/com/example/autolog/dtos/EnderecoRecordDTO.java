package com.example.autolog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRecordDTO(@NotNull String logradouro, @NotBlank String bairro, @NotBlank String localidade, @NotBlank String uf) {
}
