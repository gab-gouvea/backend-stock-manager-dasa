package br.com.estoque.dasa.modules.category.service;

public record DataListCategory(Long id, String name, String color) {

    public DataListCategory(Category category) {
        this(category.getId(), category.getName(), category.getColor());
    }
}
