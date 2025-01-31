package br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException(String message) {
        super(message);
    }
}
