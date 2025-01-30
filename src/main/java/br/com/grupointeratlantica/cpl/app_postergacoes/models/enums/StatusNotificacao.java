package br.com.grupointeratlantica.cpl.app_postergacoes.models.enums;

public enum StatusNotificacao {

    PENDENTE("Notificação pendente"),
    ENVIADO("Notificação enviada");

    private final String descricao;

    StatusNotificacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
