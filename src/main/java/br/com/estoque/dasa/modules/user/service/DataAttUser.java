package br.com.estoque.dasa.modules.user.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataAttUser(

        @NotNull
        String id,

        String name,
        String password) {
}
