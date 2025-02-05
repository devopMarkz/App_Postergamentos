package br.com.grupointeratlantica.cpl.app_postergacoes.services;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;

import java.util.List;

public interface EmailService {

    void enviarEmailComNotas(List<NotaPostergada> notasPostergadas, List<String> emailsCorporativos);

}
