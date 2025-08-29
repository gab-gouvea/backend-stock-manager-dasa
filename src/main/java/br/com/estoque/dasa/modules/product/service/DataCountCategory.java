package br.com.estoque.dasa.modules.product.service;

import java.time.LocalDateTime;

public record DataCountCategory(
        String id,
        String color,
        String name,
        String description,
        LocalDateTime createdAt,
        Long totalProducts
) {}
