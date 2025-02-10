package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro.ErroRespostaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro.ErroRespostaValidationDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.NotaPostergadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static br.com.grupointeratlantica.cpl.app_postergacoes.utils.GeradorDeURI.gerarURI;

@RestController
@RequestMapping("/postergamentos")
public class NotaPostergadaController {

    private final NotaPostergadaService notaPostergadaService;

    public NotaPostergadaController(NotaPostergadaService notaPostergadaService) {
        this.notaPostergadaService = notaPostergadaService;
    }

    @Operation(summary = "Criar uma nova nota postergada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nota postergada criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErroRespostaValidationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(schema = @Schema(implementation = ErroRespostaDTO.class)))
    })
    @PostMapping("/financeiro")
    @PreAuthorize("hasAnyRole('ROLE_FINANCEIRO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> criarNotaPostergada(@RequestBody NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){
        NotaPostergadaDTO notaPostergadaDTO = notaPostergadaService.salvar(notaPostergadaCriacaoDTO);
        return ResponseEntity.created(gerarURI(notaPostergadaDTO.id())).build();
    }

    @Operation(summary = "Buscar notas postergadas com filtros para o financeiro")
    @GetMapping("/financeiro")
    @PreAuthorize("hasAnyRole('ROLE_FINANCEIRO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<NotaPostergadaDTO>> buscarPorFiltro(
            @Parameter(description = "Data mínima para o filtro") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMinima,
            @Parameter(description = "Data máxima para o filtro") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMaxima,
            @Parameter(description = "Número único da nota") @RequestParam(required = false) Long numeroUnico,
            @Parameter(description = "Número da nota fiscal") @RequestParam(required = false) String numeroNota,
            @Parameter(description = "Código da empresa") @RequestParam(required = false) Integer codigoEmpresa,
            @Parameter(description = "Número da página para paginação") @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Tamanho da página para paginação") @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<NotaPostergadaDTO> notasPostergadas = notaPostergadaService.buscarTodasPorFiltro(
                dataMinima, dataMaxima, numeroUnico, numeroNota, codigoEmpresa, pageable
        );
        return ResponseEntity.ok(notasPostergadas);
    }

    @Operation(summary = "Buscar notas postergadas por empresa do comprador")
    @GetMapping("/usuario")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRADOR', 'ROLE_COMPRADOR', 'ROLE_USUARIO')")
    public ResponseEntity<Page<NotaPostergadaDTO>> buscarPorEmpresaDoComprador(
            @Parameter(description = "Data mínima para o filtro") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMinima,
            @Parameter(description = "Data máxima para o filtro") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMaxima,
            @Parameter(description = "Número da página para paginação") @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @Parameter(description = "Tamanho da página para paginação") @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<NotaPostergadaDTO> notasPostergadas = notaPostergadaService.buscarPorEmpresas(
                dataMinima, dataMaxima, pageable);
        return ResponseEntity.ok(notasPostergadas);
    }

    @Operation(summary = "Atualizar nota postergada pelo número único")
    @PutMapping("/financeiro")
    @PreAuthorize("hasAnyRole('ROLE_FINANCEIRO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> atualizarPorNumeroUnico(@RequestBody NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){
        notaPostergadaService.atualizar(notaPostergadaCriacaoDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar nota postergada pelo número único")
    @DeleteMapping("/financeiro/{numeroUnico}")
    @PreAuthorize("hasAnyRole('ROLE_FINANCEIRO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> deletarPorNumeroUnico(
            @Parameter(description = "Número único da nota postergada") @PathVariable Long numeroUnico){
        notaPostergadaService.deletarPorNumeroUnico(numeroUnico);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Enviar todas as notas postergadas pendentes", description = "Envia as notas pendentes para que os compradores possam visualizar tanto por e-mail como pelo sistema.")
    @PostMapping("/financeiro/enviar")
    @PreAuthorize("hasAnyRole('ROLE_FINANCEIRO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> enviarNotasPostergadas(){
        notaPostergadaService.enviarTodasAsNotasPostergadas();
        return ResponseEntity.ok().build();
    }
}
