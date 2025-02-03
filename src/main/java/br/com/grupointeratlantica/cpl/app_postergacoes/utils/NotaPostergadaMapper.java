package br.com.grupointeratlantica.cpl.app_postergacoes.utils;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Usuario;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.EmpresaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.ObterUsuarioLogadoService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.EmpresaInexistenteException;
import org.springframework.stereotype.Component;

@Component
public class NotaPostergadaMapper {

    private EmpresaRepository empresaRepository;
    private ObterUsuarioLogadoService obterUsuarioLogadoService;

    public NotaPostergadaMapper(EmpresaRepository empresaRepository, ObterUsuarioLogadoService obterUsuarioLogadoService) {
        this.empresaRepository = empresaRepository;
        this.obterUsuarioLogadoService = obterUsuarioLogadoService;
    }

    public NotaPostergada toEntity(NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){
        Empresa empresa = empresaRepository.findByCodigo(notaPostergadaCriacaoDTO.codigoEmpresa())
                .orElseThrow(() -> new EmpresaInexistenteException("Empresa de código " + notaPostergadaCriacaoDTO.codigoEmpresa() + " inexistente."));

        Usuario usuario = obterUsuarioLogadoService.obterUsuario();

        NotaPostergada notaPostergada = new NotaPostergada();
        notaPostergada.setNumeroUnico(notaPostergadaCriacaoDTO.numeroUnico());
        notaPostergada.setNumeroNota(notaPostergadaCriacaoDTO.numeroNota());
        notaPostergada.setEmpresa(empresa);
        notaPostergada.setParceiro(notaPostergadaCriacaoDTO.parceiro());
        notaPostergada.setDataVencimento(notaPostergadaCriacaoDTO.dataVencimento());
        notaPostergada.setValorDoDesdobramento(notaPostergadaCriacaoDTO.valorDoDesdobramento());
        notaPostergada.setJustificativa(notaPostergadaCriacaoDTO.justificativa());

        notaPostergada.setUsuarioRegistro(usuario);
        notaPostergada.setUsuarioAlteracao(usuario);

        return notaPostergada;
    }

    public NotaPostergadaDTO toDTO(NotaPostergada notaPostergada){
        return new NotaPostergadaDTO(
                notaPostergada.getId(),
                notaPostergada.getNumeroUnico(),
                notaPostergada.getNumeroNota(),
                notaPostergada.getEmpresa().getCodigo(),
                notaPostergada.getParceiro(),
                notaPostergada.getDataVencimento(),
                notaPostergada.getValorDoDesdobramento(),
                notaPostergada.getJustificativa(),
                notaPostergada.getStatusNotificacao(),
                notaPostergada.getDataMovimentacao(),
                notaPostergada.getDataRegistro(),
                notaPostergada.getDataAlteracao(),
                notaPostergada.getUsuarioRegistro().getEmail(),
                notaPostergada.getUsuarioAlteracao().getEmail()
        );
    }

}
