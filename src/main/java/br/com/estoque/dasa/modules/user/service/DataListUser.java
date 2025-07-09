package br.com.estoque.dasa.modules.user.service;

public record DataListUser(Long id, String name, String email) {

    public DataListUser(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }
}
