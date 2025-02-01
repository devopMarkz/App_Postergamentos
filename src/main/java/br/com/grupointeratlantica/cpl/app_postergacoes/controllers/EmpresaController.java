package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaAtualizacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.EmpresaService;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.GeradorDeURI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> criarEmpresa(@RequestBody EmpresaDTO empresaDTO){
        EmpresaDTO empresa = empresaService.salvar(empresaDTO);
        return ResponseEntity.created(GeradorDeURI.gerarURI(empresa.id())).build();
    }

    @GetMapping
    public ResponseEntity<Page<EmpresaDTO>> buscarEmpresas(@RequestParam(required = false) Integer codigo,
                                                                    @RequestParam(required = false) String nome,
                                                                    @PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<EmpresaDTO> empresas = empresaService.buscarPorFiltros(codigo, nome, pageable);
        return ResponseEntity.ok(empresas);
    }

    @PutMapping
    public ResponseEntity<Void> atualizarEmpresa(@RequestBody EmpresaAtualizacaoDTO empresaAtualizacaoDTO){
        empresaService.atualizarEmpresa(empresaAtualizacaoDTO);
        return ResponseEntity.noContent().build();
    }

}
