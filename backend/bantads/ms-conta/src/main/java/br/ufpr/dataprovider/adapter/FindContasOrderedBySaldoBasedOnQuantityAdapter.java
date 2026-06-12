package br.ufpr.dataprovider.adapter;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.FindContasOrderedBySaldoBasedOnQuantityOutputPort;
import br.ufpr.dataprovider.adapter.domain.command.ContaCommandEntity;
import br.ufpr.dataprovider.client.command.ContaCommandRepository;
import br.ufpr.dataprovider.mapper.ContaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindContasOrderedBySaldoBasedOnQuantityAdapter implements FindContasOrderedBySaldoBasedOnQuantityOutputPort {

  private final ContaCommandRepository repository;
  private final ContaEntityMapper mapper;

  @Override
  public List<Conta> find(int quantity) {

    List<ContaCommandEntity> entities = repository.findContasOrderedBySaldoBasedOnQuantity(quantity);

    List<Conta> contas = entities.stream().map(mapper::toDomain).toList();

    return contas;
  }
}
