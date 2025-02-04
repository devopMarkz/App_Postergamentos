package br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions;

public class NotaPostergadaInexistenteException extends RuntimeException {
    public NotaPostergadaInexistenteException(String message) {
        super(message);
    }
}
