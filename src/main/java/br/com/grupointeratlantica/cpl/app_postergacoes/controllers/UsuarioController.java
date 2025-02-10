package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioAtualizacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioRespostaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.UsuarioService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.impl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static br.com.grupointeratlantica.cpl.app_postergacoes.utils.GeradorDeURI.gerarURI;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Endpoints de Usuários", description = "Permite cadastrar, visualizar e atualizar usuários.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário.", description = "Endpoint público que permite a criação de um novo usuário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário criado."),
            @ApiResponse(responseCode = "400", description = "Erro de entrada de dados")
    })
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody UsuarioCriacaoDTO usuarioCriacaoDTO){
        var usuario = usuarioService.salvar(usuarioCriacaoDTO);
        return ResponseEntity.created(gerarURI(usuario.id())).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Visualizar usuários.", description = "Permite que administradores visualizem todos os usuários do sistema.")
    public ResponseEntity<Page<UsuarioRespostaDTO>> buscarUsuarios(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UsuarioRespostaDTO> usuarios = usuarioService.buscarTodos(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping
    @Operation(summary = "Atualizar senha.", description = "Permite atualizar a senha do usuário.")
    public ResponseEntity<Void> atualizarSenha(@Valid @RequestBody UsuarioAtualizacaoDTO usuarioAtualizacaoDTO){
        usuarioService.atualizarUsuario(usuarioAtualizacaoDTO);
        return ResponseEntity.noContent().build();
    }
}
