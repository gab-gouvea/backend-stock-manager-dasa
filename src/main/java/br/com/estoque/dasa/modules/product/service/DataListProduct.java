package br.com.estoque.dasa.modules.product.service;


import br.com.estoque.dasa.modules.product.entity.Product;

public record DataListProduct(String id, String name, String description) {

    public DataListProduct(Product product) {
        this(product.getId(), product.getName(), product.getDescription());
    }
}
