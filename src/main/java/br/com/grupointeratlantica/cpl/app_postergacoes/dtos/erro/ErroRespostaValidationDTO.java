package br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro;

import java.time.Instant;
import java.util.List;

public record ErroRespostaValidationDTO(
        Instant timestamp,
        Integer status,
        String path,
        List<ErroFieldsDTO> errors
) {
}
