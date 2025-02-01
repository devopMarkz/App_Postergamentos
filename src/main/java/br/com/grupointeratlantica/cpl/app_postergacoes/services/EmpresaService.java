package br.com.grupointeratlantica.cpl.app_postergacoes.services;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmpresaService {

    EmpresaDTO salvar(EmpresaDTO empresaDTO);

    Page<EmpresaDTO> buscarPorFiltros(Integer codigo, String nome, Pageable pageable);

}
