package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.GerenteEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerenteRepository extends JpaRepository<GerenteEntity, Integer> {

  GerenteEntity findByGerenteId(String gerenteId);
  boolean existsByGerenteId(String gerenteId);

  GerenteEntity findByCpf(String cpf);
  boolean existsByCpf(String cpf);

  @Transactional
  void deleteByGerenteId(String gerenteId);
}
