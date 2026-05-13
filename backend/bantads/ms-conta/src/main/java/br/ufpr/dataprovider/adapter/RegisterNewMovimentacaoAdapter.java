package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.ports.output.RegisterNewMovimentacaoOutputPort;
import br.ufpr.dataprovider.adapter.domain.MovimentacaoEntity;
import br.ufpr.dataprovider.client.MovimentacaoRepository;
import br.ufpr.dataprovider.mapper.MovimentacaoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterNewMovimentacaoAdapter implements RegisterNewMovimentacaoOutputPort {

  private final MovimentacaoRepository repository;
  private final MovimentacaoEntityMapper mapper;

  @Override
  public Movimentacao register(Movimentacao movimentacao) {

    MovimentacaoEntity entity = mapper.toEntity(movimentacao);

    MovimentacaoEntity newEntity = repository.save(entity);

    return mapper.toDomain(newEntity);

  }
}
