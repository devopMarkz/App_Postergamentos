package br.com.grupointeratlantica.cpl.app_postergacoes.services;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.Usuario;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ObterUsuarioLogadoService {

    private final UsuarioRepository usuarioRepository;

    public ObterUsuarioLogadoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario obterUsuario(){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(login).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }

}
