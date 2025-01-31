package br.com.grupointeratlantica.cpl.app_postergacoes.repositories;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT obj FROM Usuario obj JOIN FETCH obj.roles WHERE obj.email = :email")
    Optional<Usuario> findByEmailWithRoles(String email);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

}
