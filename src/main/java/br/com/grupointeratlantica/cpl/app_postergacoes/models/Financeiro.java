package br.com.grupointeratlantica.cpl.app_postergacoes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("FINANCEIRO") // Define o valor da coluna tipo_usuario para essa subclasse
public class Financeiro extends Usuario {

    public Financeiro() {}

    public Financeiro(Long id, String email, String senha) {
        super(id, email, senha);
    }
}