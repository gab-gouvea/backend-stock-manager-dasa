package br.com.estoque.dasa.modules.product.service;

import br.com.estoque.dasa.modules.category.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record DataCreateProduct(

        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotNull
        @PositiveOrZero
        Long quantity,

        @NotNull
        @PositiveOrZero
        Long minQuantity,

        @NotBlank
        String categoryId
) {

}
