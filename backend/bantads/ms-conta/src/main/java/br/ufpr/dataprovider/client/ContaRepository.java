package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.ContaEntity;
import br.ufpr.dataprovider.adapter.GerenteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, Integer> {

  @Query(value = "SELECT nomeGerente, cpfGerente FROM contas GROUP BY cpfGerente ORDER BY COUNT(cpfCliente) ASC LIMIT 1", nativeQuery = true)
  GerenteInfo getGerenteWithFewerClientes();
}
