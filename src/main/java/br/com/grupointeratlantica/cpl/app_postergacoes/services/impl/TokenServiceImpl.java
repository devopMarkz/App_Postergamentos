package br.com.grupointeratlantica.cpl.app_postergacoes.services.impl;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.login.AuthDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Role;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Usuario;
import br.com.grupointeratlantica.cpl.app_postergacoes.repositories.UsuarioRepository;
import br.com.grupointeratlantica.cpl.app_postergacoes.services.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    private UsuarioRepository usuarioRepository;
    private String secretKey;

    public TokenServiceImpl(UsuarioRepository usuarioRepository, @Value("${secret-key}") String secretKey) {
        this.usuarioRepository = usuarioRepository;
        this.secretKey = secretKey;
    }

    @Override
    public String obterToken(AuthDTO authDTO) {
        Usuario usuario = usuarioRepository.findByEmailWithRoles(authDTO.login())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        return gerarToken(usuario);
    }

    private String gerarToken(Usuario usuario){
        List<String> roles = usuario.getRoles().stream().map(Role::getAuthority).toList();
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("app_postergamentos")
                    .withSubject(usuario.getEmail())
                    .withClaim("Roles", roles)
                    .withExpiresAt(gerarTempoDeExpiracaoDoToken())
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw new JWTCreationException("Erro na criação do Token.", e);
        }
    }

    private Instant gerarTempoDeExpiracaoDoToken(){
        return LocalDateTime.now().plusHours(8L).toInstant(ZoneOffset.of("-03:00"));
    }

    @Override
    public String validarTokenERetornarLoginDeUsuario(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("app_postergamentos")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException e){
            throw new JWTCreationException("Token inválido.", e);
        }
    }
}
