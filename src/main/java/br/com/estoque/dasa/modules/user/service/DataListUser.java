package br.com.estoque.dasa.modules.user.service;

import br.com.estoque.dasa.modules.user.entity.User;

public record DataListUser(String id, String name, String email) {

    public DataListUser(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }
}
