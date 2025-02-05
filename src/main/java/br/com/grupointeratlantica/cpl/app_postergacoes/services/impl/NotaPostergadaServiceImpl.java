package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaCriacaoDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.notaPostergada.NotaPostergadaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Usuario;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.enums.StatusNotificacao;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.EmpresaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.NotaPostergadaRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.UsuarioRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.EmailService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.NotaPostergadaService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.ObterUsuarioLogadoService;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.*;
import br.com.grupointeratlantica.cpl.app_postergacoes.utils.NotaPostergadaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class NotaPostergadaServiceImpl implements NotaPostergadaService {

    private NotaPostergadaRepository notaPostergadaRepository;
    private UsuarioRepository usuarioRepository;
    private EmpresaRepository empresaRepository;
    private NotaPostergadaMapper notaPostergadaMapper;
    private ObterUsuarioLogadoService obterUsuarioLogadoService;
    private EmailService emailService;

    public NotaPostergadaServiceImpl(NotaPostergadaRepository notaPostergadaRepository, UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository, NotaPostergadaMapper notaPostergadaMapper, ObterUsuarioLogadoService obterUsuarioLogadoService, EmailService emailService) {
        this.notaPostergadaRepository = notaPostergadaRepository;
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
        this.notaPostergadaMapper = notaPostergadaMapper;
        this.obterUsuarioLogadoService = obterUsuarioLogadoService;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public NotaPostergadaDTO salvar(NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){
        if(notaPostergadaRepository.existsByNumeroUnico(notaPostergadaCriacaoDTO.numeroUnico())) {
            throw new NotaPostergadaJaExistenteException("Postergação com número único " + notaPostergadaCriacaoDTO.numeroUnico() + " já foi inclusa.");
        }
        NotaPostergada notaPostergada = notaPostergadaMapper.toEntity(notaPostergadaCriacaoDTO);
        NotaPostergada novaNotaPostergada = notaPostergadaRepository.save(notaPostergada);
        return notaPostergadaMapper.toDTO(novaNotaPostergada);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotaPostergadaDTO> buscarTodasPorFiltro(LocalDate dataMinima, LocalDate dataMaxima, Long numeroUnico, String numeroNota, Integer codigoEmpresa, Pageable pageable){
        Page<NotaPostergada> notasPostergadas = notaPostergadaRepository.pesquisarNotasPorFiltros(dataMinima, dataMaxima, numeroUnico, numeroNota, codigoEmpresa, pageable);
        return notasPostergadas.map(notaPostergada -> notaPostergadaMapper.toDTO(notaPostergada));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotaPostergadaDTO> buscarPorEmpresas(LocalDate dataMinima, LocalDate dataMaxima, Pageable pageable){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByEmailWithEmpresas(login)
                .orElseThrow(() -> new UsuarioInexistenteException("Usuário não encontrado"));

        Page<NotaPostergada> notasPostergadas = notaPostergadaRepository.pesquisarNotasPorEmpresas(usuario.getEmpresas(), dataMinima, dataMaxima, pageable);

        return notasPostergadas.map(notaPostergada -> notaPostergadaMapper.toDTO(notaPostergada));
    }

    @Override
    @Transactional
    public void atualizar(NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){
        NotaPostergada notaPostergada = notaPostergadaRepository.findByNumeroUnico(notaPostergadaCriacaoDTO.numeroUnico())
                .orElseThrow(() -> new NotaPostergadaInexistenteException("Nota inexistente."));
        NotaPostergada notaAtualizada = atualizarNota(notaPostergada, notaPostergadaCriacaoDTO);
        notaPostergadaRepository.save(notaAtualizada);
    }

    @Override
    @Transactional
    public void deletarPorNumeroUnico(Long numeroUnico){
        if(!notaPostergadaRepository.existsByNumeroUnico(numeroUnico)) throw new NotaPostergadaInexistenteException("Postergamento inexistente.");
        notaPostergadaRepository.deleteByNumeroUnico(numeroUnico);
    }

    @Override
    @Transactional
    public void enviarTodasAsNotasPostergadas(){
        List<NotaPostergada> notasPostergadas = notaPostergadaRepository.obterNotasPendentesDeEnvio();

        if(notasPostergadas.isEmpty()){
            throw new EmailException("Não há notas pendentes de envio!");
        }

        List<String> emailsCorporativos = empresaRepository.findAll().stream().map(Empresa::getEmailCorporativo).toList();

        emailService.enviarEmailComNotas(
                notasPostergadas.stream().sorted(Comparator.comparing(o -> o.getEmpresa().getNome())).toList(),
                emailsCorporativos
        );

        notasPostergadas.forEach(notaPostergada -> notaPostergada.setStatusNotificacao(StatusNotificacao.ENVIADO));
    }

    private NotaPostergada atualizarNota(NotaPostergada notaPostergada, NotaPostergadaCriacaoDTO notaPostergadaCriacaoDTO){
        notaPostergada.setEmpresa(empresaRepository.findByCodigo(notaPostergadaCriacaoDTO.codigoEmpresa())
                .orElseThrow(() -> new EmpresaInexistenteException("Empresa de código " + notaPostergadaCriacaoDTO.codigoEmpresa() + " inexistente.")));
        notaPostergada.setParceiro(notaPostergadaCriacaoDTO.parceiro());
        notaPostergada.setDataVencimento(notaPostergadaCriacaoDTO.dataVencimento());
        notaPostergada.setValorDoDesdobramento(notaPostergadaCriacaoDTO.valorDoDesdobramento());
        notaPostergada.setJustificativa(notaPostergadaCriacaoDTO.justificativa());
        notaPostergada.setUsuarioAlteracao(obterUsuarioLogadoService.obterUsuario());
        return notaPostergada;
    }

}
