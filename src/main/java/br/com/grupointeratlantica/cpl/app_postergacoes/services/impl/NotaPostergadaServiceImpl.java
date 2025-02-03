package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.NotaPostergadaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotaPostergadaServiceImpl {

    private NotaPostergadaRepository notaPostergadaRepository;

    public NotaPostergadaServiceImpl(NotaPostergadaRepository notaPostergadaRepository) {
        this.notaPostergadaRepository = notaPostergadaRepository;
    }

    @Transactional
    public NotaPostergadaDTO salvar(NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){

        return null;
    }

}
