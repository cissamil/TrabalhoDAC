package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.BatchSyncContaOutputPort;
import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import br.ufpr.dataprovider.mapper.query.ContaQueryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchSyncContaAdapter implements BatchSyncContaOutputPort {

  private final ContaQueryRepository repository;
  private final ContaQueryEntityMapper mapper;

  @Override
  public void sync(List<Conta> contas) {

    List<ContaQueryEntity> entityList = contas
      .stream()
      .map(mapper::toEntity)
      .toList();

    repository.saveAll(entityList);
  }
}
