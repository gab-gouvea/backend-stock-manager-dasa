package br.com.estoque.dasa.modules.product_log.service;

import br.com.estoque.dasa.modules.product.entity.Product;
import br.com.estoque.dasa.modules.product_log.entity.ProductLog;

import java.time.LocalDateTime;

public record DataListLogs(String id, EnumAction action, String cpf, Long quantity, LocalDateTime createdAt, Product product) {

    public DataListLogs(ProductLog productLog) {
        this(productLog.getId(), productLog.getAction(), productLog.getCpf(), productLog.getQuantity(), productLog.getCreatedAt(), productLog.getProduct());
    }
}
