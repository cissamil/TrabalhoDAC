package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.ports.output.SyncMovimentacaoOutputPort;
import br.ufpr.dataprovider.adapter.domain.query.MovimentacaoQueryEntity;
import br.ufpr.dataprovider.client.query.MovimentacaoQueryRepository;
import br.ufpr.dataprovider.mapper.query.MovimentacaoQueryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SyncMovimentacaoAdapter implements SyncMovimentacaoOutputPort {

  private final MovimentacaoQueryRepository repository;
  private final MovimentacaoQueryEntityMapper mapper;

  @Override
  public void sync(Movimentacao movimentacao) {

    MovimentacaoQueryEntity entity = mapper.toEntity(movimentacao);

    repository.save(entity);
  }
}
