package br.com.estoque.dasa.modules.product_log.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record WrapperStockOut(

        @NotNull
        @Valid
        List<DataRemoval> itens,

        @NotBlank
        String withdrawBy
) {
}
