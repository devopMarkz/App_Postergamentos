package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.impl.EmpresaServiceImpl;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.GeradorDeURI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private EmpresaServiceImpl empresaService;

    public EmpresaController(EmpresaServiceImpl empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
    public ResponseEntity<Void> criarEmpresa(@RequestBody EmpresaDTO empresaDTO){
        EmpresaDTO empresa = empresaService.salvar(empresaDTO);
        return ResponseEntity.created(GeradorDeURI.gerarURI(empresa.id())).build();
    }

}
