package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro.ErroRespostaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro.ErroRespostaValidationDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioAtualizacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.usuario.UsuarioRespostaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.UsuarioService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.impl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(
        name = "Endpoints de Usuários",
        description = "APIs para gestão de usuários, incluindo criação, listagem e atualização de senhas. Apenas administradores podem listar usuários."
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário.", description = "Endpoint público que permite a criação de um novo usuário no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(
                    responseCode = "400", description = "Erro de entrada de dados.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaValidationDTO.class))
            ),
            @ApiResponse(responseCode = "409", description = "Conflito. E-mail já cadastrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaDTO.class))
            )
    })
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody UsuarioCriacaoDTO usuarioCriacaoDTO){
        var usuario = usuarioService.salvar(usuarioCriacaoDTO);
        return ResponseEntity.created(gerarURI(usuario.id())).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Operation(summary = "Visualizar usuários.", description = "Permite que administradores visualizem todos os usuários do sistema.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de usuários retornada com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioRespostaDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido.")
    })
    public ResponseEntity<Page<UsuarioRespostaDTO>> buscarUsuarios(
            @Parameter(description = "Número da página para paginação", example = "0") @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Tamanho da página para paginação", example = "10") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UsuarioRespostaDTO> usuarios = usuarioService.buscarTodos(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping
    @Operation(summary = "Atualizar senha.", description = "Permite atualizar a senha do usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso."),
            @ApiResponse(
                    responseCode = "400", description = "Erro de entrada de dados.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaValidationDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Senha incorreta.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Usuário não encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaDTO.class))
            )
    })
    public ResponseEntity<Void> atualizarSenha(@Valid @RequestBody UsuarioAtualizacaoDTO usuarioAtualizacaoDTO){
        usuarioService.atualizarUsuario(usuarioAtualizacaoDTO);
        return ResponseEntity.noContent().build();
    }
}
