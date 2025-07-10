package br.com.estoque.dasa.modules.user.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DataCreateUser(

        @NotBlank
        String name,

        @Email
        String email,
        String password) {
}
