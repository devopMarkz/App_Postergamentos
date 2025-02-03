package br.com.grupointeratlantica.cpl.app_postergacoes.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", nullable = false, unique = true)
    private Integer codigo;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email_corporativo", nullable = false, unique = true)
    private String emailCorporativo;

    @ManyToMany(mappedBy = "empresas")
    List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "empresa")
    private List<NotaPostergada> notaPostergadas = new ArrayList<>();

    public Empresa() {
    }

    public Empresa(Integer id, Integer codigo, String nome, String emailCorporativo) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.emailCorporativo = emailCorporativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailCorporativo() {
        return emailCorporativo;
    }

    public void setEmailCorporativo(String emailCorporativo) {
        this.emailCorporativo = emailCorporativo;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<NotaPostergada> getNotaPostergadas() {
        return notaPostergadas;
    }

    public void setNotaPostergadas(List<NotaPostergada> notaPostergadas) {
        this.notaPostergadas = notaPostergadas;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Empresa empresa = (Empresa) object;
        return Objects.equals(id, empresa.id) && Objects.equals(codigo, empresa.codigo) && Objects.equals(nome, empresa.nome) && Objects.equals(emailCorporativo, empresa.emailCorporativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, nome, emailCorporativo);
    }
}
