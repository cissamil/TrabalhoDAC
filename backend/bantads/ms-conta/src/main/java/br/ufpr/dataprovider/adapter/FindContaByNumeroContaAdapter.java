package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaByNumeroContaOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaByNumeroContaAdapter implements FindContaByNumeroContaOutputPort {

  private final ContaEntityMapper mapper;
  private final ContaCommandRepository repository;

  @Override
  public Conta find(String numeroConta) {

    ContaCommandEntity entity = repository.findByNumeroConta(numeroConta);

    return mapper.toDomain(entity);
  }

  @Override
  public boolean exists(String numeroConta) {
    return repository.existsByNumeroConta(numeroConta);
  }
}
