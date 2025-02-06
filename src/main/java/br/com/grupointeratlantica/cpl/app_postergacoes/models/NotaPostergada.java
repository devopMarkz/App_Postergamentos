package br.com.grupointeratlantica.cpl.app_postergacoes.models;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.enums.StatusNotificacao;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_nota_postergada")
public class NotaPostergada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_unico", nullable = false, unique = true)
    private Long numeroUnico;

    @Column(name = "numero_nota")
    private String numeroNota;

    @ManyToOne
    @JoinColumn(name = "empresa")
    private Empresa empresa;

    @Column(name = "parceiro")
    private String parceiro;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "valor_do_desdobramento")
    private Double valorDoDesdobramento;

    @Column(name = "justificativa", columnDefinition = "TEXT")
    private String justificativa;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_notificacao", nullable = false)
    private StatusNotificacao statusNotificacao = StatusNotificacao.PENDENTE; // Padrão: PENDENTE

    @CreationTimestamp
    @Column(name = "data_movimentacao")
    private LocalDate dataMovimentacao;

    @CreationTimestamp
    @Column(name = "data_registro")
    private LocalDate dataRegistro;

    @UpdateTimestamp
    @Column(name = "data_alteracao")
    private LocalDate dataAlteracao;

    @ManyToOne
    @JoinColumn(name = "usuario_registro", referencedColumnName = "email")
    private Usuario usuarioRegistro;

    @ManyToOne
    @JoinColumn(name = "usuario_alteracao", referencedColumnName = "email")
    private Usuario usuarioAlteracao;

    public NotaPostergada() {
    }

    public NotaPostergada(Long id, Long numeroUnico, String numeroNota, Empresa empresa, String parceiro, LocalDate dataVencimento, Double valorDoDesdobramento, String justificativa, StatusNotificacao statusNotificacao, Usuario usuarioRegistro, Usuario usuarioAlteracao) {
        this.id = id;
        this.numeroUnico = numeroUnico;
        this.numeroNota = numeroNota;
        this.empresa = empresa;
        this.parceiro = parceiro;
        this.dataVencimento = dataVencimento;
        this.valorDoDesdobramento = valorDoDesdobramento;
        this.justificativa = justificativa;
        this.statusNotificacao = statusNotificacao;
        this.usuarioRegistro = usuarioRegistro;
        this.usuarioAlteracao = usuarioAlteracao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroUnico() {
        return numeroUnico;
    }

    public void setNumeroUnico(Long numeroUnico) {
        this.numeroUnico = numeroUnico;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getParceiro() {
        return parceiro;
    }

    public void setParceiro(String parceiro) {
        this.parceiro = parceiro;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValorDoDesdobramento() {
        return valorDoDesdobramento;
    }

    public void setValorDoDesdobramento(Double valorDoDesdobramento) {
        this.valorDoDesdobramento = valorDoDesdobramento;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public void setStatusNotificacao(StatusNotificacao statusNotificacao) {
        this.statusNotificacao = statusNotificacao;
    }

    public LocalDate getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDate dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Usuario getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(Usuario usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public Usuario getUsuarioAlteracao() {
        return usuarioAlteracao;
    }

    public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }

    public void marcarComoEnviado() {
        this.statusNotificacao = StatusNotificacao.ENVIADO;
    }

    public void marcarComoPendente() {
        this.statusNotificacao = StatusNotificacao.PENDENTE;
    }

    public boolean isNotificacaoPendente() {
        return this.statusNotificacao == StatusNotificacao.PENDENTE;
    }

    public StatusNotificacao getStatusNotificacao() {
        return statusNotificacao;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NotaPostergada that = (NotaPostergada) object;
        return Objects.equals(numeroUnico, that.numeroUnico);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numeroUnico);
    }
}
