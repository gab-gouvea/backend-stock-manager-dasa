package br.com.estoque.dasa.modules.product.service;


public record DataListProduct(Long id, String name, String description) {

    public DataListProduct(Product product) {
        this(product.getId(), product.getName(), product.getDescription());
    }
}
