package br.com.grupointeratlantica.cpl.app_postergacoes.controllers;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.login.AuthDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public String autenticarUsuario(@RequestBody AuthDTO authDTO){
        var authentication = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.senha());
        authenticationManager.authenticate(authentication);
        return "OK";
    }

}
