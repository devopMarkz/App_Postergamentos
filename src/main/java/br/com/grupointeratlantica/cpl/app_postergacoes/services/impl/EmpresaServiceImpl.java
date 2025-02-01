package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.EmpresaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.EmpresaJaExistenteException;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.UsuarioJaCadastradoException;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.EmpresaMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpresaServiceImpl {

    private EmpresaRepository empresaRepository;
    private EmpresaMapper empresaMapper;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    @Transactional
    public EmpresaDTO salvar(EmpresaDTO empresaDTO){
        if(empresaRepository.existsByCodigoOrNomeOrEmailCorporativo(empresaDTO.codigo(), empresaDTO.nome(), empresaDTO.emailCorporativo())){
            throw new EmpresaJaExistenteException("Empresa já cadastrada.");
        }
        Empresa empresa = empresaMapper.toEntity(empresaDTO);
        Empresa novaEmpresa = empresaRepository.save(empresa);
        return empresaMapper.toDTO(empresa);
    }

    @Transactional(readOnly = true)
    public Page<EmpresaDTO> buscarPorFiltros(Integer codigo, String nome, Pageable pageable){
        Empresa empresaPrototipo = new Empresa(null, codigo, nome, null);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Empresa> example = Example.of(empresaPrototipo, exampleMatcher);
        Page<Empresa> empresas = empresaRepository.findAll(example, pageable);
        return empresas.map(empresa -> empresaMapper.toDTO(empresa));
    }

}
