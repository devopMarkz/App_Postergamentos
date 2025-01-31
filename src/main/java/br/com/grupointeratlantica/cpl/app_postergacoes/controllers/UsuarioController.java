package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioRespostaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.impl.UsuarioService;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.GeradorDeURI;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody UsuarioCriacaoDTO usuarioCriacaoDTO){
        var usuario = usuarioService.salvar(usuarioCriacaoDTO);
        return ResponseEntity.created(GeradorDeURI.gerarURI(usuario.id())).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Page<UsuarioRespostaDTO>> buscarUsuarios(@PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<UsuarioRespostaDTO> usuarios = usuarioService.buscarTodos(pageable);
        return ResponseEntity.ok(usuarios);
    }
}
