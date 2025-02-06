package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaAtualizacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.EmpresaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.EmpresaService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.EmpresaInexistenteException;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.EmpresaJaExistenteException;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.ErroDeIntegridadeReferencialException;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.EmpresaMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    @Override
    @Transactional
    public EmpresaDTO salvar(EmpresaDTO empresaDTO){
        if(empresaRepository.existsByCodigoOrNomeOrEmailCorporativo(empresaDTO.codigo(), empresaDTO.nome(), empresaDTO.emailCorporativo())){
            throw new EmpresaJaExistenteException("Empresa já cadastrada.");
        }
        Empresa empresa = empresaMapper.toEntity(empresaDTO);
        Empresa novaEmpresa = empresaRepository.save(empresa);
        return empresaMapper.toDTO(empresa);
    }

    @Override
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

    @Transactional
    public void atualizarEmpresa(EmpresaAtualizacaoDTO empresaAtualizacaoDTO){
        Empresa empresa = empresaRepository.findById(empresaAtualizacaoDTO.id())
                .orElseThrow(() -> new EmpresaInexistenteException("Empresa inexistente."));

        empresa.setCodigo(empresaAtualizacaoDTO.codigo() == null? empresa.getCodigo() : empresaAtualizacaoDTO.codigo());
        empresa.setNome(empresaAtualizacaoDTO.nome() == null? empresa.getNome() : empresaAtualizacaoDTO.nome());
        empresa.setEmailCorporativo(empresaAtualizacaoDTO.emailCorporativo() == null? empresa.getEmailCorporativo() : empresaAtualizacaoDTO.emailCorporativo());

        empresaRepository.save(empresa);
    }

    @Transactional
    public void deletarEmpresaPorId(Integer id){
        if(!empresaRepository.existsById(id)) throw new EmpresaInexistenteException("Empresa inexistente.");
        try {
            empresaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new ErroDeIntegridadeReferencialException("A entidade em questão já tem relação com outra entidade. Não pode ser excluída.");
        }

    }

}
