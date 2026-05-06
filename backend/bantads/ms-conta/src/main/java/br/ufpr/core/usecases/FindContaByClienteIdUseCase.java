package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.FindContaByClienteIdInputPort;
import br.ufpr.core.ports.output.FindContaByClienteIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindContaByClienteIdUseCase implements FindContaByClienteIdInputPort {

  private final FindContaByClienteIdOutputPort findContaByClienteIdOutputPort;

  @Override
  public Conta find(String clienteId) {
    return findContaByClienteIdOutputPort.find(clienteId);
  }
}
