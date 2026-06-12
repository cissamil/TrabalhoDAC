package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContasByGerenteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindContasByGerenteIdAdapter implements FindContasByGerenteIdOutputPort {

  private final ContaCommandRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public List<Conta> find(String gerenteId) {

    List<ContaCommandEntity> entities = repository.findByGerenteId(gerenteId);

    return entities.stream().map(mapper::toDomain).toList();
  }
}
