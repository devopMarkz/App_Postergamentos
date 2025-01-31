package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioAtualizacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioRespostaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Usuario;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.EmpresaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.RoleRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.UsuarioRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.SenhaIncorretaException;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.UsuarioInexistenteException;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.UsuarioJaCadastradoException;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.UsuarioMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private RoleRepository roleRepository;
    private EmpresaRepository empresaRepository;
    private UsuarioMapper usuarioMapper;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, EmpresaRepository empresaRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.empresaRepository = empresaRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioRespostaDTO salvar(UsuarioCriacaoDTO usuarioCriacaoDTO){
        if(usuarioRepository.existsByEmail(usuarioCriacaoDTO.email())) throw new UsuarioJaCadastradoException("Usuário já cadastrado.");
        Usuario usuario = usuarioMapper.toEntity(usuarioCriacaoDTO, passwordEncoder);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioRespostaDTO> buscarTodos(Pageable pageable){
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(usuario -> usuarioMapper.toDTO(usuario));
    }

    @Transactional
    public void atualizarUsuario(UsuarioAtualizacaoDTO usuarioAtualizacaoDTO){
        if(!usuarioRepository.existsByEmail(usuarioAtualizacaoDTO.login())) {
            throw new UsuarioInexistenteException("Usuário inexistente.");
        }

        Usuario usuario = usuarioRepository.findByEmail(usuarioAtualizacaoDTO.login()).get();

        if(!passwordEncoder.matches(usuarioAtualizacaoDTO.senha(), usuario.getSenha())) {
            throw new SenhaIncorretaException("A senha informada deve ser igual a senha que está sendo utilizada atualmente.");
        }

        usuario.setSenha(passwordEncoder.encode(usuarioAtualizacaoDTO.novaSenha()));

        usuarioRepository.save(usuario);
    }

}
