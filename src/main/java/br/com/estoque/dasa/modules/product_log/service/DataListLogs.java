package br.com.estoque.dasa.modules.product_log.service;

import br.com.estoque.dasa.modules.product.entity.Product;
import br.com.estoque.dasa.modules.product_log.entity.ProductLog;

public record DataListLogs(String id, Action action, String cpf, Long quantity, Product product) {

    public DataListLogs(ProductLog productLog) {
        this(productLog.getId(), productLog.getAction(), productLog.getCpf(), productLog.getQuantity(), productLog.getProduct());
    }
}
