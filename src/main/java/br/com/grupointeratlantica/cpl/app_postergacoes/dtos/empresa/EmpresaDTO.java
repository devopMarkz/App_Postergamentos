package br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EmpresaDTO(

        Integer id,

        @NotNull(message = "O código da empresa não pode ser nulo.")
        @Positive(message = "O código da empresa deve ser um número positivo.")
        Integer codigo,

        @NotBlank(message = "O nome da empresa não pode estar em branco.")
        String nome,

        @NotBlank(message = "O e-mail corporativo não pode estar em branco.")
        @Email(message = "O e-mail corporativo deve ser um endereço válido.")
        String emailCorporativo

) {}
