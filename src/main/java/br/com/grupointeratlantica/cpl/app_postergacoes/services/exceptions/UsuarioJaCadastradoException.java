package br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions;

public class UsuarioJaCadastradoException extends RuntimeException {
    public UsuarioJaCadastradoException(String message) {
        super(message);
    }
}
