package br.com.grupointeratlantica.cpl.app_postergacoes.services;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface NotaPostergadaService {

    NotaPostergadaDTO salvar(NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO);

    Page<NotaPostergadaDTO> buscarTodasPorFiltro(LocalDate dataMinima, LocalDate dataMaxima, Long numeroUnico, String numeroNota, Integer codigoEmpresa, Pageable pageable);

    Page<NotaPostergadaDTO> buscarPorEmpresas(LocalDate dataMinima, LocalDate dataMaxima, Pageable pageable);

    void atualizar(NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO);

    void deletarPorNumeroUnico(Long numeroUnico);

    void enviarTodasAsNotasPostergadas();

}
