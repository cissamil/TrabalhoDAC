package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.ports.output.RegisterNewMovimentacaoOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.MovimentacaoCommandEntity;
import br.ufpr.dataprovider.client.command.MovimentacaoCommandRepository;
import br.ufpr.dataprovider.mapper.MovimentacaoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterNewMovimentacaoAdapter implements RegisterNewMovimentacaoOutputPort {

  private final MovimentacaoCommandRepository repository;
  private final MovimentacaoEntityMapper mapper;

  @Override
  public Movimentacao register(Movimentacao movimentacao) {

    MovimentacaoCommandEntity entity = mapper.toEntity(movimentacao);

    MovimentacaoCommandEntity newEntity = repository.save(entity);

    return mapper.toDomain(newEntity);

  }
}
