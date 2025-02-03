package br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record NotaPostergadaCriacaoDTO(

        @NotNull(message = "O número único do financeiro é obrigatório.")
        Long numeroUnico,

        String numeroNota,

        @NotNull(message = "O código da empresa é obrigatório.")
        @Positive(message = "O código da empresa deve ser um número positivo.")
        Integer codigoEmpresa,

        @NotBlank(message = "O parceiro não pode estar em branco.")
        String parceiro,

        @NotNull(message = "A data de vencimento é obrigatória.")
        @FutureOrPresent(message = "A data de vencimento não pode ser no passado.")
        LocalDate dataVencimento,

        @NotNull(message = "O valor do desdobramento é obrigatório.")
        @DecimalMin(value = "0.01", message = "O valor do desdobramento deve ser maior que zero.")
        Double valorDoDesdobramento,

        String justificativa
) {}
