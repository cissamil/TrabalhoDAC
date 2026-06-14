package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaByNumeroContaOutputPort;
import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import br.ufpr.dataprovider.mapper.query.ContaQueryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaByNumeroContaAdapter implements FindContaByNumeroContaOutputPort {

  private final ContaQueryEntityMapper mapper;
  private final ContaQueryRepository repository;

  @Override
  public Conta find(String numeroConta) {

    ContaQueryEntity entity = repository.findByNumeroConta(numeroConta);

    return mapper.toDomain(entity);
  }

  @Override
  public boolean exists(String numeroConta) {
    return repository.existsByNumeroConta(numeroConta);
  }
}
