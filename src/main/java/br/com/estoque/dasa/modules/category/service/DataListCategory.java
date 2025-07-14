package br.com.estoque.dasa.modules.category.service;

import br.com.estoque.dasa.modules.category.entity.Category;

import java.time.LocalDateTime;

public record DataListCategory(String id, String name, String color, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public DataListCategory(Category category) {
        this(category.getId(), category.getName(), category.getColor(), category.getCreatedAt(), category.getUpdatedAt());
    }
}
