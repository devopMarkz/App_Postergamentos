package br.com.grupointeratlantica.cpl.app_postergacoes.dtos;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Role;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Usuario;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.enums.TipoUsuario;

import java.util.List;

public record UsuarioRespostaDTO(
        Long id,
        String email,
        TipoUsuario tipoUsuario,
        List<String> roles, // Nome das permissões associadas
        List<String> empresas // Nome das empresas associadas
) {

    public UsuarioRespostaDTO converterParaDTO(Usuario usuario) {
        return new UsuarioRespostaDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getTipoUsuario(),
                usuario.getRoles().stream().map(Role::getAuthority).toList(),
                usuario.getEmpresas().stream().map(Empresa::getNome).toList()
        );
    }


}
