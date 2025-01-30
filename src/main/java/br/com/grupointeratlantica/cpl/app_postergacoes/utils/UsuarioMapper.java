package br.com.grupointeratlantica.cpl.app_postergacoes.utils;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.UsuarioCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.UsuarioRespostaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Role;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Usuario;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.enums.TipoUsuario;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.EmpresaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioMapper {

    private final RoleRepository roleRepository;
    private final EmpresaRepository empresaRepository;

    public UsuarioMapper(RoleRepository roleRepository, EmpresaRepository empresaRepository) {
        this.roleRepository = roleRepository;
        this.empresaRepository = empresaRepository;
    }

    public Usuario toEntity(UsuarioCriacaoDTO usuarioCriacaoDTO, PasswordEncoder passwordEncoder) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioCriacaoDTO.email());
        usuario.setSenha(passwordEncoder.encode(usuarioCriacaoDTO.senha()));
        usuario.setTipoUsuario(TipoUsuario.valueOf(usuarioCriacaoDTO.tipoUsuario()));

        // Mapeia as roles
        List<Role> roles = new ArrayList<>(roleRepository.findByRoleIn(usuarioCriacaoDTO.roles()));
        usuario.setRoles(roles);

        // Mapeia as empresas
        List<Empresa> empresas = empresaRepository.findByCodigoIn(usuarioCriacaoDTO.codigosEmpresas());
        usuario.setEmpresas(empresas);

        return usuario;
    }

    public UsuarioRespostaDTO toDTO(Usuario usuario){
        // Mapeia as roles
        List<String> roles = usuario.getRoles().stream().map(Role::getAuthority).toList();

        // Mapeia as empresas
        List<String> empresas = usuario.getEmpresas().stream().map(Empresa::getNome).toList();

        return new UsuarioRespostaDTO(usuario.getId(), usuario.getEmail(), usuario.getTipoUsuario(), roles, empresas);
    }
}
