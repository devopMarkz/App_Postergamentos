package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.UsuarioCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.impl.UsuarioService;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.GeradorDeURI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> criarUsuario(@RequestBody UsuarioCriacaoDTO usuarioCriacaoDTO){
        var usuario = usuarioService.salvar(usuarioCriacaoDTO);
        return ResponseEntity.created(GeradorDeURI.gerarURI(usuario.id())).build();
    }
}
