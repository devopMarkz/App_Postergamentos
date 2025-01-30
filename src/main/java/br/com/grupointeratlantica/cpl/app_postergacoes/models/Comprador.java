package br.com.grupointeratlantica.cpl.app_postergacoes.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("COMPRADOR") // Define o valor da coluna tipo_usuario para essa subclasse
public class Comprador extends Usuario {

    @ManyToMany
    @JoinTable(name = "tb_comprador_empresa",
            joinColumns = @JoinColumn(name = "id_comprador"),
            inverseJoinColumns = @JoinColumn(name = "id_empresa"))
    private List<Empresa> empresas = new ArrayList<>();

    public Comprador() {}

    public Comprador(Long id, String email, String senha) {
        super(id, email, senha);
    }

    public List<Empresa> getEmpresas() { return empresas; }

    public void setEmpresas(List<Empresa> empresas) { this.empresas = empresas; }
}