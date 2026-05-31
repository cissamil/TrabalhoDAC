package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.FindContasOrderedBySaldoBasedOnQuantityOutputPort;
import br.ufpr.dataprovider.adapter.domain.ContaEntity;
import br.ufpr.dataprovider.client.ContaRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindContasOrderedBySaldoBasedOnQuantityAdapter implements FindContasOrderedBySaldoBasedOnQuantityOutputPort {

  private final ContaRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public List<Conta> find(int quantity) {

    List<ContaEntity> entities = repository.findContasOrderedBySaldoBasedOnQuantity(quantity);

    List<Conta> contas = entities.stream().map(mapper::toDomain).toList();

    return contas;
  }
}
