package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.SyncContaOutputPort;
import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import br.ufpr.dataprovider.mapper.query.ContaQueryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SyncContaAdapter implements SyncContaOutputPort {


  private final ContaQueryRepository repository;
  private final ContaQueryEntityMapper mapper;

  @Override
  public void sync(Conta conta) {

    ContaQueryEntity entity = mapper.toEntity(conta);

    repository.save(entity);
  }
}
