package br.com.estoque.dasa.modules.category.service;

import jakarta.validation.constraints.NotNull;

public record DataAttCategory(

        @NotNull
        String id,

        String name,
        String color) {
}
