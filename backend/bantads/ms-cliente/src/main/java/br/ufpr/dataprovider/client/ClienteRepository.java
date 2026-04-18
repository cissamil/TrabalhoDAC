package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository  extends JpaRepository<ClienteEntity, Integer> {

  boolean existsByCpf(String cpf);
}
