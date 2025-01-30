package br.com.grupointeratlantica.cpl.app_postergacoes.dtos;

import java.util.List;

public record UsuarioRespostaDTO(
        Long id,
        String email,
        String tipoUsuario,
        List<String> roles, // Nome das permissões associadas
        List<String> empresas // Nome das empresas associadas
) {}
