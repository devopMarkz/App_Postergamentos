package br.com.grupointeratlantica.cpl.app_postergacoes.repositories;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NotaPostergadaRepository extends JpaRepository<NotaPostergada, Long> {

    @Query("""
    SELECT n FROM NotaPostergada n
    WHERE (COALESCE(:dataMinima, n.dataMovimentacao) = n.dataMovimentacao OR n.dataMovimentacao >= :dataMinima)
    AND (COALESCE(:dataMaxima, n.dataMovimentacao) = n.dataMovimentacao OR n.dataMovimentacao <= :dataMaxima)
    AND (:numeroUnico IS NULL OR n.numeroUnico = :numeroUnico)
    AND (:numeroNota IS NULL OR n.numeroNota LIKE %:numeroNota%)
    AND (:codigoEmpresa IS NULL OR n.empresa.codigo = :codigoEmpresa)
    """)
    Page<NotaPostergada> pesquisarNotasPorFiltros(
            @Param("dataMinima") LocalDate dataMinima,
            @Param("dataMaxima") LocalDate dataMaxima,
            @Param("numeroUnico") Long numeroUnico,
            @Param("numeroNota") String numeroNota,
            @Param("codigoEmpresa") Integer codigoEmpresa,
            Pageable pageable
    );

    @Query("SELECT n FROM NotaPostergada n " +
            "WHERE n.empresa IN :empresas " +
            "AND (COALESCE(:dataMinima, n.dataMovimentacao) = n.dataMovimentacao OR n.dataMovimentacao >= :dataMinima) " +
            "AND (COALESCE(:dataMaxima, n.dataMovimentacao) = n.dataMovimentacao OR n.dataMovimentacao <= :dataMaxima) " +
            "AND n.statusNotificacao = 'ENVIADO'")
    Page<NotaPostergada> pesquisarNotasPorEmpresas(@Param("empresas") List<Empresa> empresas,
                                                   @Param("dataMinima") LocalDate dataMinima,
                                                   @Param("dataMaxima") LocalDate dataMaxima,
                                                   Pageable pageable);

    @Query("SELECT obj FROM NotaPostergada obj WHERE obj.statusNotificacao = 'PENDENTE'")
    List<NotaPostergada> obterNotasPendentesDeEnvio();

    Optional<NotaPostergada> findByNumeroUnico(Long numeroUnico);

    boolean existsByNumeroUnico(Long numeroUnico);

    void deleteByNumeroUnico(Long numeroUnico);

}
