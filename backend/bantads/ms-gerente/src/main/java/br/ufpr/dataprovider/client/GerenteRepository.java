package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.GerenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerenteRepository extends JpaRepository<GerenteEntity, Integer> {

}
