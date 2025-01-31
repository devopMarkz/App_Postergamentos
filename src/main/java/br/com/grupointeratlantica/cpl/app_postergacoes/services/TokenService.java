package br.com.grupointeratlantica.cpl.app_postergacoes.services;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.login.AuthDTO;

public interface TokenService {

    String obterToken(AuthDTO authDTO);
    String validarTokenERetornarLoginDeUsuario(String token);

}
