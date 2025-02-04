package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.impl.NotaPostergadaServiceImpl;
import org.springframework.data.domain.Page;
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
public class NotaPostergadaFinanceiroController {

    private NotaPostergadaServiceImpl notaPostergadaService;

    public NotaPostergadaFinanceiroController(NotaPostergadaServiceImpl notaPostergadaService) {
        this.notaPostergadaService = notaPostergadaService;
    }

    @PostMapping("/financeiro")
    @PreAuthorize("hasAnyRole('ROLE_FINANCEIRO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> criarNotaPostergada(@RequestBody NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){
        NotaPostergadaDTO notaPostergadaDTO = notaPostergadaService.salvar(notaPostergadaCriacaoDTO);
        return ResponseEntity.created(gerarURI(notaPostergadaDTO.id())).build();
    }

    @GetMapping("/financeiro")
    @PreAuthorize("hasAnyRole('ROLE_FINANCEIRO', 'ROLE_ADMINISTRADOR')")
    public ResponseEntity<Page<NotaPostergadaDTO>> buscarPorFiltro( @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMinima,
                                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMaxima,
                                                                @RequestParam(required = false) Long numeroUnico,
                                                                @RequestParam(required = false) String numeroNota,
                                                                @RequestParam(required = false) Integer codigoEmpresa,
                                                                @PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<NotaPostergadaDTO> notasPostergadas = notaPostergadaService.buscarTodasPorFiltro(dataMinima, dataMaxima, numeroUnico, numeroNota, codigoEmpresa, pageable);
        return ResponseEntity.ok(notasPostergadas);
    }
}
