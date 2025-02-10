package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaAtualizacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro.ErroRespostaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro.ErroRespostaValidationDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static br.com.grupointeratlantica.cpl.app_postergacoes.utils.GeradorDeURI.gerarURI;

@RestController
@RequestMapping("/empresas")
@Tag(name = "Endpoints de Empresas", description = "Permite cadastrar, visualizar, atualizar e deletar empresas.")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @Operation(summary = "Cadastrar empresa", description = "Permite que administradores cadastrem uma nova empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de entrada de dados.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaValidationDTO.class)))
    })
    public ResponseEntity<Void> criarEmpresa(@RequestBody EmpresaDTO empresaDTO){
        EmpresaDTO empresa = empresaService.salvar(empresaDTO);
        return ResponseEntity.created(gerarURI(empresa.id())).build();
    }

    @GetMapping
    @Operation(summary = "Buscar empresas", description = "Permite buscar empresas por código ou nome, com suporte à paginação.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresas encontradas com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhuma empresa encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaDTO.class)))
    })
    public ResponseEntity<Page<EmpresaDTO>> buscarEmpresas(
            @Parameter(description = "Código da empresa") @RequestParam(required = false) Integer codigo,
            @Parameter(description = "Nome da empresa") @RequestParam(required = false) String nome,
            @Parameter(description = "Número da página para paginação") @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Tamanho da página para paginação") @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<EmpresaDTO> empresas = empresaService.buscarPorFiltros(codigo, nome, pageable);
        return ResponseEntity.ok(empresas);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @Operation(summary = "Atualizar empresa", description = "Permite que administradores atualizem os dados de uma empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empresa atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de entrada de dados.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaValidationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaDTO.class)))
    })
    public ResponseEntity<Void> atualizarEmpresa(@RequestBody EmpresaAtualizacaoDTO empresaAtualizacaoDTO){
        empresaService.atualizarEmpresa(empresaAtualizacaoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    @Operation(summary = "Deletar empresa", description = "Permite que administradores deletem uma empresa pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empresa deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroRespostaDTO.class)))
    })
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Integer id){
        empresaService.deletarEmpresaPorId(id);
        return ResponseEntity.noContent().build();
    }
}
