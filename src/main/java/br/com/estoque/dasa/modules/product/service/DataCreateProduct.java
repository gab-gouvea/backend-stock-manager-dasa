package br.com.estoque.dasa.modules.product.service;

import jakarta.validation.constraints.NotBlank;

public record DataCreateProduct(

        @NotBlank
        String name,

        @NotBlank
        String description) {
}
