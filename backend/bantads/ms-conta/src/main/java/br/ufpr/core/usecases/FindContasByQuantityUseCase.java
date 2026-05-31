package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.FindContasByQuantityInputPort;
import br.ufpr.core.ports.input.FindContasOrderedBySaldoBasedOnQuantityOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindContasByQuantityUseCase implements FindContasByQuantityInputPort {

  private final FindContasOrderedBySaldoBasedOnQuantityOutputPort findContasOrderedBySaldoBasedOnQuantityOutputPort;

  @Override
  public List<Conta> find(int quantity) {
    return findContasOrderedBySaldoBasedOnQuantityOutputPort.find(quantity);
  }
}
