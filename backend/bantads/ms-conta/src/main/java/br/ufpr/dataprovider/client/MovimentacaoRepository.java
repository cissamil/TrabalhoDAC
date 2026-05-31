package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.MovimentacaoEntity;
import feign.Param;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Integer>{

  @Query(
    value = "SELECT * FROM movimentacoes " +
    "WHERE data_hora >= :dataInicio " +
    "AND data_hora <= :dataFim "  +
    "AND cliente_origem_id = :clienteId",
    nativeQuery = true
  )
  List<MovimentacaoEntity> findMovimentacoesByClienteIdBetweenDates(
    @Param("dataInicio") LocalDateTime dataInicio,
    @Param("dataFim") LocalDateTime dataFim,
    @Param("clienteId") String clienteId
  );

}
