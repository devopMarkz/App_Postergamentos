package br.com.grupointeratlantica.cpl.app_postergacoes.repositories;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
