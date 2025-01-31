package br.com.grupointeratlantica.cpl.app_postergacoes.services;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioAtualizacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioRespostaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    UsuarioRespostaDTO salvar(UsuarioCriacaoDTO usuarioCriacaoDTO);

    Page<UsuarioRespostaDTO> buscarTodos(Pageable pageable);

    void atualizarUsuario(UsuarioAtualizacaoDTO usuarioAtualizacaoDTO);

}