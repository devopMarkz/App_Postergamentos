package br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro;

import java.time.Instant;

public record ErroRespostaDTO(
        Instant timestamp,
        Integer status,
        String message,
        String path
) {
}
