package br.com.grupointeratlantica.cpl.app_postergacoes.repositories;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface NotaPostergadaRepository extends JpaRepository<NotaPostergada, Long> {

    @Query("""
        SELECT n FROM NotaPostergada n
        WHERE (:dataMinima IS NULL OR n.dataMovimentacao >= :dataMinima)
        AND (:dataMaxima IS NULL OR n.dataMovimentacao <= :dataMaxima)
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

    boolean existsByNumeroUnico(Long numeroUnico);

}
