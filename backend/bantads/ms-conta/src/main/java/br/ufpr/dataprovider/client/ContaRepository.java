package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, Integer> {

  @Query(
    value =
      "SELECT gerente_id FROM contas " +
      "WHERE status_conta = 'CONTA_APROVADA' " +
      "GROUP BY gerente_id " +
      "ORDER BY COUNT(cliente_id) ASC LIMIT 1",
    nativeQuery = true
  )
  String findGerenteIdWithFewerClientes();

  boolean existsByNumeroConta(Integer numeroConta);
  ContaEntity findByNumeroConta(Integer numeroConta);

}
