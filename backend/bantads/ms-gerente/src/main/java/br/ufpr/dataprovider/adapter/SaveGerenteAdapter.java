package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.ports.output.SaveGerenteOutputPort;
import br.ufpr.dataprovider.adapter.domain.GerenteEntity;
import br.ufpr.dataprovider.client.GerenteRepository;
import br.ufpr.dataprovider.mapper.GerenteEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveGerenteAdapter implements SaveGerenteOutputPort {

  private final GerenteEntityMapper mapper;
  private final GerenteRepository repository;

  @Override
  public Gerente save(Gerente gerente) {

    GerenteEntity entity = mapper.toEntity(gerente);

    GerenteEntity newEntity = repository.save(entity);

    return mapper.toDomain(newEntity);
  }
}
