package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContasByGerenteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import br.ufpr.dataprovider.mapper.query.ContaQueryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindContasByGerenteIdAdapter implements FindContasByGerenteIdOutputPort {

  private final ContaQueryRepository repository;
  private final ContaQueryEntityMapper mapper;

  @Override
  public List<Conta> find(String gerenteId) {

    List<ContaQueryEntity> entities = repository.findByGerenteId(gerenteId);

    return entities.stream().map(mapper::toDomain).toList();
  }
}
