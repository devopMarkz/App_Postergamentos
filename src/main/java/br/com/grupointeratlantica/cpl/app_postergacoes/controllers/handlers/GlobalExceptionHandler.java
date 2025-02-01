package br.com.grupointeratlantica.cpl.app_postergacoes.controllers.handlers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro.ErroFieldsDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro.ErroRespostaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.erro.ErroRespostaValidationDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.EmpresaJaExistenteException;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.SenhaIncorretaException;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.UsuarioInexistenteException;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.exceptions.UsuarioJaCadastradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErroRespostaDTO> usernameNotFound(UsernameNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroRespostaDTO erroRespostaDTO = new ErroRespostaDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroRespostaDTO);
    }

    @ExceptionHandler(UsuarioJaCadastradoException.class)
    public ResponseEntity<ErroRespostaDTO> usuarioJaCadastrado(UsuarioJaCadastradoException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ErroRespostaDTO erroRespostaDTO = new ErroRespostaDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroRespostaDTO);
    }

    @ExceptionHandler(EmpresaJaExistenteException.class)
    public ResponseEntity<ErroRespostaDTO> usuarioJaCadastrado(EmpresaJaExistenteException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ErroRespostaDTO erroRespostaDTO = new ErroRespostaDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroRespostaDTO);
    }

    @ExceptionHandler(UsuarioInexistenteException.class)
    public ResponseEntity<ErroRespostaDTO> usuarioJaCadastrado(UsuarioInexistenteException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroRespostaDTO erroRespostaDTO = new ErroRespostaDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroRespostaDTO);
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<ErroRespostaDTO> usuarioJaCadastrado(SenhaIncorretaException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroRespostaDTO erroRespostaDTO = new ErroRespostaDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erroRespostaDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRespostaValidationDTO> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ErroFieldsDTO> errors = new ArrayList<>();
        for (FieldError error : e.getFieldErrors()){
            String field = error.getField(); // Atributo da classe Motorista que apresentou inconformidade
            String message = error.getDefaultMessage(); // message definida nas annotations de validação
            errors.add(new ErroFieldsDTO(field, message));
        }
        ErroRespostaValidationDTO errorMessageDto = new ErroRespostaValidationDTO(Instant.now(), status.value(), request.getRequestURI(), errors);
        return ResponseEntity.status(status.value()).body(errorMessageDto);
    }

}
