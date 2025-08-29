package br.com.estoque.dasa.modules.product.service;

import java.time.LocalDateTime;

public record DataCountCategory(
        String categoryId,
        String categoryName,
        String categoryDescription,
        LocalDateTime createdAt,
        Long totalProducts

) {}
