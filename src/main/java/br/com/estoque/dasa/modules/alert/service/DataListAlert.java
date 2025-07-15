package br.com.estoque.dasa.modules.alert.service;

import br.com.estoque.dasa.modules.alert.entity.Alert;
import br.com.estoque.dasa.modules.product.entity.Product;

import java.time.LocalDateTime;

public record DataListAlert(String id, EnumTipo tipo, Boolean status, String message, LocalDateTime createdAt, Product product) {
    public DataListAlert(Alert alert) {
        this(alert.getId(), alert.getTipo(), alert.getStatus(), alert.getMessage(), alert.getCreatedAt(), alert.getProduct());
    }
}
