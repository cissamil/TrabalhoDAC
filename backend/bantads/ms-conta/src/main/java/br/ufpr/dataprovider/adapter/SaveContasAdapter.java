package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.output.SaveContasOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveContasAdapter implements SaveContasOutputPort {

  private final ContaRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  @Transactional
  public List<Conta> save(List<Conta> contas) {

    List<ContaEntity> entities = contas.stream().map(mapper::toEntity).toList();

    List<ContaEntity> newEntities =  repository.saveAll(entities);

    return newEntities.stream().map(mapper::toDomain).toList();
  }
}
