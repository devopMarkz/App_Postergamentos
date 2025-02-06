package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.login.AuthDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.token.TokenDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> autenticarUsuario(@RequestBody AuthDTO authDTO, HttpServletRequest request) {
        log.info("Requisição recebida: Método={} | Endpoint={} | Usuário={}",
                request.getMethod(), request.getRequestURI(), authDTO.login().substring(0, 3) + "***");

        var authentication = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.senha());
        authenticationManager.authenticate(authentication);

        log.info("Usuário {} autenticado com sucesso.", authDTO.login().substring(0, 3) + "***");

        String accessToken = tokenService.obterToken(authDTO);
        String expiresIn = tokenService.retornarTempoDeExpiracaoDoToken(accessToken);

        return ResponseEntity.ok(new TokenDTO(accessToken, expiresIn));
    }

}
