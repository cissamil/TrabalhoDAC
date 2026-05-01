package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.FindContaByIdOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaByIdAdapter implements FindContaByIdOutputPort {

  private final ContaRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public Conta find(Integer contaId) {

    ContaEntity entity = repository.findById(contaId).orElse(null);

    return mapper.toDomain(entity);
  }
}
