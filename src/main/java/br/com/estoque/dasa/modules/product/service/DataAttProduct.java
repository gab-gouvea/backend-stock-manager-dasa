package br.com.estoque.dasa.modules.product.service;

import jakarta.validation.constraints.NotNull;

public record DataAttProduct(

        @NotNull
        String id,

        String name,
        String description) {
}
