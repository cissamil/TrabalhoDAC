package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaByClienteIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaByClienteIdAdapter implements FindContaByClienteIdOutputPort {

  private final ContaRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public Conta find(String clienteId) {

    ContaEntity entity = repository.findByClienteId(clienteId);

    return mapper.toDomain(entity);
  }
}
