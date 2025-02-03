package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.NotaPostergadaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.NotaPostergadaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
