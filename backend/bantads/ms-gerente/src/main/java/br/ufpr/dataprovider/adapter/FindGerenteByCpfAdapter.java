package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.ports.output.FindGerenteByCpfOutputPort;
import br.ufpr.dataprovider.adapter.domain.GerenteEntity;
import br.ufpr.dataprovider.client.GerenteRepository;
import br.ufpr.dataprovider.mapper.GerenteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGerenteByCpfAdapter implements FindGerenteByCpfOutputPort {

  private final GerenteRepository repository;
  private final GerenteEntityMapper mapper;

  @Override
  public Gerente find(String cpf) {

    GerenteEntity entity = repository.findByCpf(cpf);

    return mapper.toDomain(entity);
  }

  @Override
  public boolean exists(String cpf) {
    return repository.existsByCpf(cpf);
  }
}
