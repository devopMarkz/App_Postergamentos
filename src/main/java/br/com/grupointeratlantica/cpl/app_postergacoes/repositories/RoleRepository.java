package br.com.grupointeratlantica.cpl.app_postergacoes.repositories;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
