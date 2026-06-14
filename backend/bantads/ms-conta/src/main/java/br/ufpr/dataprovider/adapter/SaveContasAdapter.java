package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.SaveContasOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.mapper.command.ContaCommandEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveContasAdapter implements SaveContasOutputPort {

  private final ContaCommandRepository repository;
  private final ContaCommandEntityMapper mapper;

  @Override
  @Transactional
  public List<Conta> save(List<Conta> contas) {

    List<ContaCommandEntity> entities = contas.stream().map(mapper::toEntity).toList();

    List<ContaCommandEntity> newEntities =  repository.saveAll(entities);

    return newEntities.stream().map(mapper::toDomain).toList();
  }
}
