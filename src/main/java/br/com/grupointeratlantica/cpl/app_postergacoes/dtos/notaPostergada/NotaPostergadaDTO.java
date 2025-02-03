package br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.enums.StatusNotificacao;

import java.time.LocalDate;

public record NotaPostergadaDTO(
        Long id,
        Long numeroUnico,
        String numeroNota,
        Integer codigoEmpresa,
        String parceiro,
        LocalDate dataVencimento,
        Double valorDoDesdobramento,
        String justificativa,
        StatusNotificacao statusNotificacao,
        LocalDate dataRegistro,
        LocalDate dataAlteracao,
        String usuarioRegistro,
        String usuarioAlteracao
) {}