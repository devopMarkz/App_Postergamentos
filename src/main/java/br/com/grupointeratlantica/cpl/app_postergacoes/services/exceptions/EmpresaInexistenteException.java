package br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions;

public class EmpresaInexistenteException extends RuntimeException {
    public EmpresaInexistenteException(String message) {
        super(message);
    }
}
