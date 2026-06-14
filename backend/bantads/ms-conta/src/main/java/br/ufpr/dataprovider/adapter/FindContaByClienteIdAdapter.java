package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaByClienteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import br.ufpr.dataprovider.mapper.command.ContaCommandEntityMapper;
import br.ufpr.dataprovider.mapper.query.ContaQueryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaByClienteIdAdapter implements FindContaByClienteIdOutputPort {

  private final ContaQueryRepository repository;
  private final ContaQueryEntityMapper mapper;

  @Override
  public Conta find(String clienteId) {

    ContaQueryEntity entity = repository.findByClienteId(clienteId);

    return mapper.toDomain(entity);
  }
}
