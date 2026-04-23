package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveContaAdapter implements SaveContaOutputPort {

  private final ContaEntityMapper mapper;
  private final ContaRepository repository;

  public Conta save(Conta conta){

    ContaEntity entity = mapper.toEntity(conta);

    ContaEntity savedEntity = repository.save(entity);

    return mapper.toDomain(savedEntity);

  }
}
