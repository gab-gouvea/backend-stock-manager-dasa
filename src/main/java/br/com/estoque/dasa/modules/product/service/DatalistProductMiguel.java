package br.com.estoque.dasa.modules.product.service;

import br.com.estoque.dasa.modules.product.entity.Product;

import java.time.LocalDateTime;

public record DatalistProductMiguel(String id, String name, Long quantity, LocalDateTime createdAt) {

    public DatalistProductMiguel(Product product) {
        this(product.getId(), product.getName(), product.getQuantity(),  product.getCreatedAt());
    }
}
