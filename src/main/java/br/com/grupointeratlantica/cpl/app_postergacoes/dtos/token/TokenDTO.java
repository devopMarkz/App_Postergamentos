package br.com.grupointeratlantica.cpl.app_postergacoes.dtos.token;

public record TokenDTO(
        String access_token,
        String expires_in
) {
}
