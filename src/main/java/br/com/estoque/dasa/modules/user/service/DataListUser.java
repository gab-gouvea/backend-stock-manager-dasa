package br.com.estoque.dasa.modules.user.service;

import br.com.estoque.dasa.modules.user.entity.User;

import java.time.LocalDateTime;

public record DataListUser(String id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {

    public DataListUser(User user) {

        this(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(),user.getUpdatedAt());
    }
}
