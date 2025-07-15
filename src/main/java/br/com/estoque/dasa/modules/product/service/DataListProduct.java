package br.com.estoque.dasa.modules.product.service;


import br.com.estoque.dasa.modules.product.entity.Product;

import java.time.LocalDateTime;

public record DataListProduct(String id, String name, String description, Long quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public DataListProduct(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getCreatedAt(), product.getUpdatedAt());
    }
}
