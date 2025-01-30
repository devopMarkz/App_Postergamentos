package br.com.grupointeratlantica.cpl.app_postergacoes.repositories;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
}
