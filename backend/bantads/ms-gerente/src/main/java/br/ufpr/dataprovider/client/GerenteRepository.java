package br.ufpr.dataprovider.client;

import br.ufpr.core.domain.Gerente;
import br.ufpr.dataprovider.adapter.domain.GerenteEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GerenteRepository extends JpaRepository<GerenteEntity, Integer> {

  GerenteEntity findByGerenteId(String gerenteId);
  boolean existsByGerenteId(String gerenteId);

  GerenteEntity findByCpf(String cpf);
  boolean existsByCpf(String cpf);

  @Transactional
  void deleteByGerenteId(String gerenteId);

  @Query(value = "SELECT * FROM gerentes WHERE tipo_gerente = 'GERENTE'",nativeQuery = true)
  List<GerenteEntity> findGerentes();

  List<GerenteEntity> findByGerenteIdIn(List<String> gerenteIds);

}
