package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.login.AuthDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.token.TokenDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticação de Usuário")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Operation(summary = "Autenticar usuário",
    description = "Informe usuário e senha para obter um token JWT e utilizá-lo para efetuar requisições no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário autenticado."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado.")
    })
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
