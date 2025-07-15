package br.com.estoque.dasa.modules.product_log.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataJoin(

        @NotBlank
        String cpf,

        @NotNull
        @Min(1)
        Long quantity) {
}
