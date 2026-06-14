package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaByContaIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import br.ufpr.dataprovider.mapper.query.ContaQueryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaByContaIdAdapter implements FindContaByContaIdOutputPort {

  private final ContaQueryRepository repository;
  private final ContaQueryEntityMapper mapper;

  @Override
  public Conta find(String contaId) {

    ContaQueryEntity entity = repository.findByContaId(contaId);

    return mapper.toDomain(entity);
  }

  @Override
  public boolean exists(String contaId) {
    return repository.existsByContaId(contaId);
  }
}
