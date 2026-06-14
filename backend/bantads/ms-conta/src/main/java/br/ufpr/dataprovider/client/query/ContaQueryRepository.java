package br.ufpr.dataprovider.client.query;

import br.ufpr.core.domain.StatusConta;
import br.ufpr.dataprovider.adapter.domain.GerenteTotalClientesResponse;
import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContaQueryRepository extends JpaRepository<ContaQueryEntity, Integer> {


  @Query(
    value =
      "SELECT gerente_id FROM contas " +
        "WHERE status_conta = 'CONTA_APROVADA' " +
        "GROUP BY gerente_id " +
        "ORDER BY COUNT(cliente_id) ASC LIMIT 1",
    nativeQuery = true
  )
  String findGerenteWithFewerClientesId();

  @Query(
    value =
      "SELECT gerente_id FROM contas " +
        "WHERE status_conta = 'CONTA_APROVADA' " +
        "AND gerente_id != :gerenteId " +
        "GROUP BY gerente_id " +
        "ORDER BY COUNT(cliente_id) ASC LIMIT 1",
    nativeQuery = true
  )
  String findGerenteWithFewerClientesIdExceptSelectedGerente(@Param("gerenteId") String gerenteId);


  @Query(
    value =
      "SELECT gerente_id FROM contas " +
        "WHERE status_conta = 'CONTA_APROVADA' " +
        "GROUP BY gerente_id " +
        "ORDER BY COUNT(cliente_id) DESC LIMIT 1",
    nativeQuery = true
  )
  String findGerenteWithMostClientesId();

  List<ContaQueryEntity> findByGerenteId(String gerenteId);
  ContaQueryEntity findFirstByGerenteId(String gerenteId);


  boolean existsByNumeroConta(String numeroConta);
  ContaQueryEntity findByNumeroConta(String numeroConta);

  ContaQueryEntity findByContaId(String contaId);
  boolean existsByContaId(String contaId);

  List<ContaQueryEntity> findByGerenteIdAndStatusConta(String gerenteId, StatusConta statusConta);

  List<ContaQueryEntity> findByStatusConta(StatusConta statusConta);


  ContaQueryEntity findByClienteId(String clienteId);

  @Query(
    value =
      "SELECT * FROM contas " +
        "WHERE status_conta = 'CONTA_APROVADA' " +
        "ORDER BY saldo DESC " +
        "LIMIT ?1" ,
    nativeQuery = true
  )
  List<ContaQueryEntity> findContasOrderedBySaldoBasedOnQuantity(int quantity);

  @Query(
    value =
      """
        SELECT gerente_id as gerente,
        COUNT(cliente_id) as qtd_clientes,
        MIN(CASE WHEN saldo >= 0 then saldo else null end) as menor_saldo_positivo
        FROM contas WHERE status_conta = 'CONTA_APROVADA'
        GROUP BY gerente
        ORDER BY qtd_clientes DESC, menor_saldo_positivo ASC nulls last
      """,
    nativeQuery = true
  )
  List<GerenteTotalClientesResponse> findGerentesTotalClientes();


  @Query(
    value =
      """
        SELECT * FROM contas
        WHERE gerente_id = :gerenteId
        ORDER BY saldo ASC limit 1
      """,
    nativeQuery = true
  )
  ContaQueryEntity findContaWithMenorSaldoByGerenteId(@Param("gerenteId") String gerenteId);
}
