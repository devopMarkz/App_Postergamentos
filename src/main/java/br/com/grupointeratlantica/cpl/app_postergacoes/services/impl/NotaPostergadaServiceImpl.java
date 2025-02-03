package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.NotaPostergadaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.NotaPostergadaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class NotaPostergadaServiceImpl {

    private NotaPostergadaRepository notaPostergadaRepository;
    private NotaPostergadaMapper notaPostergadaMapper;

    public NotaPostergadaServiceImpl(NotaPostergadaRepository notaPostergadaRepository, NotaPostergadaMapper notaPostergadaMapper) {
        this.notaPostergadaRepository = notaPostergadaRepository;
        this.notaPostergadaMapper = notaPostergadaMapper;
    }

    @Transactional
    public NotaPostergadaDTO salvar(NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){
        NotaPostergada notaPostergada = notaPostergadaMapper.toEntity(notaPostergadaCriacaoDTO);
        NotaPostergada novaNotaPostergada = notaPostergadaRepository.save(notaPostergada);
        return notaPostergadaMapper.toDTO(novaNotaPostergada);
    }

    @Transactional(readOnly = true)
    public Page<NotaPostergadaDTO> buscarTodasPorFiltro(LocalDate dataMinima, LocalDate dataMaxima, Long numeroUnico, String numeroNota, Integer codigoEmpresa, Pageable pageable){
        Page<NotaPostergada> notasPostergadas = notaPostergadaRepository.pesquisarNotasPorFiltros(dataMinima, dataMaxima, numeroUnico, numeroNota, codigoEmpresa, pageable);
        return notasPostergadas.map(notaPostergada -> notaPostergadaMapper.toDTO(notaPostergada));
    }

}
