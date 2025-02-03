package br.com.grupointeratlantica.cpl.app_postergacoes.repositories;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    // Metodo para buscar empresas pelo código
    List<Empresa> findByCodigoIn(List<Integer> codigos);

    boolean existsByCodigoOrNomeOrEmailCorporativo(Integer codigo, String nome, String emailCorporativo);

    Optional<Empresa> findByCodigo(Integer codigo);

}
