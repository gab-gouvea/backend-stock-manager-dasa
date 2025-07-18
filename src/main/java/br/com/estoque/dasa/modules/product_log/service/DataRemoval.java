package br.com.estoque.dasa.modules.product_log.service;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRemoval(

        @NotNull
        EnumAction action,

        @Min(1)
        @NotNull
        Long quantity,

        @NotBlank
        String cpf) {
}
