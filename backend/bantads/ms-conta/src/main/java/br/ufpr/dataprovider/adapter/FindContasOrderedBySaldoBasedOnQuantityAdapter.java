package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.FindContasOrderedBySaldoBasedOnQuantityOutputPort;
import br.ufpr.dataprovider.adapter.domain.query.ContaQueryEntity;
import br.ufpr.dataprovider.client.query.ContaQueryRepository;
import br.ufpr.dataprovider.mapper.query.ContaQueryEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindContasOrderedBySaldoBasedOnQuantityAdapter implements FindContasOrderedBySaldoBasedOnQuantityOutputPort {

  private final ContaQueryRepository repository;
  private final ContaQueryEntityMapper mapper;

  @Override
  public List<Conta> find(int quantity) {

    List<ContaQueryEntity> entities = repository.findContasOrderedBySaldoBasedOnQuantity(quantity);

    List<Conta> contas = entities.stream().map(mapper::toDomain).toList();

    return contas;
  }
}
