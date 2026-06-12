package br.ufpr.dataprovider.client.query;

import br.ufpr.dataprovider.adapter.domain.command.MovimentacaoCommandEntity;
import br.ufpr.dataprovider.adapter.domain.query.MovimentacaoQueryEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoQueryRepository extends JpaRepository<MovimentacaoQueryEntity, Integer>{

  @Query(
    value = "SELECT * FROM movimentacoes " +
    "WHERE data_hora >= :dataInicio " +
    "AND data_hora <= :dataFim "  +
    "AND cliente_origem_id = :clienteId",
    nativeQuery = true
  )
  List<MovimentacaoCommandEntity> findMovimentacoesByClienteIdBetweenDates(
    @Param("dataInicio") LocalDateTime dataInicio,
    @Param("dataFim") LocalDateTime dataFim,
    @Param("clienteId") String clienteId
  );

}
