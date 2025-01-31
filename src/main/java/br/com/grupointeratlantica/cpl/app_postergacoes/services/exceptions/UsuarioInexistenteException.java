package br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions;

public class UsuarioInexistenteException extends RuntimeException {
    public UsuarioInexistenteException(String message) {
        super(message);
    }
}
