package br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message);
    }
}
