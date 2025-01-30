package br.com.grupointeratlantica.cpl.app_postergacoes.dtos;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UsuarioCriacaoDTO(
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        String senha,

        @NotNull(message = "O tipo de usuário é obrigatório")
        String tipoUsuario,

        List<Integer> codigosEmpresas, // codigos das empresas associadas

        List<String> roles // nomes das roles
) {}
