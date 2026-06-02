package br.ufpr.dataprovider.client;

import br.ufpr.dataprovider.adapter.domain.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRespository extends JpaRepository<EnderecoEntity, Long> {
}
