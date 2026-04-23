package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, Integer> {

  @Query(value = "SELECT id_gerente FROM contas GROUP BY id_gerente ORDER BY COUNT(id_cliente) ASC LIMIT 1", nativeQuery = true)
  String getGerenteWithFewerClientes();

  boolean existsByNumeroConta(Integer numeroConta);
  ContaEntity findByNumeroConta(Integer numeroConta);

}
