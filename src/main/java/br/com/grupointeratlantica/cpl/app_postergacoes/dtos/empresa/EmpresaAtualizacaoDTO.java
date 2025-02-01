package br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa;

public record EmpresaAtualizacaoDTO(
        Integer id,
        Integer codigo,
        String nome,
        String emailCorporativo
) {}