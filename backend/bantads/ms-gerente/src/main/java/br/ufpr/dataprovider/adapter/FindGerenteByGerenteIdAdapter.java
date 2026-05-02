package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.ports.output.FindGerenteByGerenteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.GerenteEntity;
import br.ufpr.dataprovider.client.GerenteRepository;
import br.ufpr.dataprovider.mapper.GerenteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindGerenteByGerenteIdAdapter implements FindGerenteByGerenteIdOutputPort {

  private final GerenteRepository repository;
  private final GerenteEntityMapper mapper;

  @Override
  public Gerente find(String gerenteId) {

    GerenteEntity entity = repository.findByGerenteId(gerenteId);

    return mapper.toDomain(entity);

  }
}
