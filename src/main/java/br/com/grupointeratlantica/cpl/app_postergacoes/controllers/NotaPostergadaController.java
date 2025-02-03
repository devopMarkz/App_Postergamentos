package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.impl.NotaPostergadaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.grupointeratlantica.cpl.app_postergacoes.utils.GeradorDeURI.gerarURI;

@RestController
@RequestMapping("/postergamentos")
public class NotaPostergadaController {

    private NotaPostergadaServiceImpl notaPostergadaService;

    public NotaPostergadaController(NotaPostergadaServiceImpl notaPostergadaService) {
        this.notaPostergadaService = notaPostergadaService;
    }

    @PostMapping
    public ResponseEntity<Void> criarNotaPostergada(@RequestBody NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){
        NotaPostergadaDTO notaPostergadaDTO = notaPostergadaService.salvar(notaPostergadaCriacaoDTO);
        return ResponseEntity.created(gerarURI(notaPostergadaDTO.id())).build();
    }

}
