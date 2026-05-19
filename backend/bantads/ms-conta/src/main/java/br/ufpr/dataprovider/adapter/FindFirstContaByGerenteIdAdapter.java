package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindFirstContaByGerenteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindFirstContaByGerenteIdAdapter implements FindFirstContaByGerenteIdOutputPort {

  private final ContaRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public Conta find(String gerenteId) {

    ContaEntity entity = repository.findFirstByGerenteId(gerenteId);

    return mapper.toDomain(entity);
  }
}
