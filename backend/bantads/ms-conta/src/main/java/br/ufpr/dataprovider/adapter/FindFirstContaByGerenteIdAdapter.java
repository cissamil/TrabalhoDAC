package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindFirstContaByGerenteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindFirstContaByGerenteIdAdapter implements FindFirstContaByGerenteIdOutputPort {

  private final ContaCommandRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public Conta find(String gerenteId) {

    ContaCommandEntity entity = repository.findFirstByGerenteId(gerenteId);

    return mapper.toDomain(entity);
  }
}
