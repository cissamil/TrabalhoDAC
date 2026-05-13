package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaByNumeroContaOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaByNumeroContaAdapter implements FindContaByNumeroContaOutputPort {

  private final ContaEntityMapper mapper;
  private final ContaRepository repository;

  @Override
  public Conta find(String numeroConta) {

    ContaEntity entity = repository.findByNumeroConta(numeroConta);

    return mapper.toDomain(entity);
  }

  @Override
  public boolean exists(Integer numeroConta) {
    return repository.existsByNumeroConta(numeroConta);
  }
}
