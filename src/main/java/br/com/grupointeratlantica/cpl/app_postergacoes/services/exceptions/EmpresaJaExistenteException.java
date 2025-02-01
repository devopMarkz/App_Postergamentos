package br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions;

public class EmpresaJaExistenteException extends RuntimeException {
    public EmpresaJaExistenteException(String message) {
        super(message);
    }
}
