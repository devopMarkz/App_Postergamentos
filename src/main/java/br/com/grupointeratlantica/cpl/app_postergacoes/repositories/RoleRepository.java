package br.com.grupointeratlantica.cpl.app_postergacoes.repositories;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.Role;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Metodo para buscar roles pelo nome (RoleEnum)
    List<Role> findByRoleIn(List<String> roles);

}
