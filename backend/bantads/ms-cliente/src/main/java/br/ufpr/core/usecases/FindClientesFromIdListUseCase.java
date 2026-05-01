package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.input.FindClientesFromIdListInputPort;
import br.ufpr.core.ports.output.FindClientesFromIdListOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindClientesFromIdListUseCase implements FindClientesFromIdListInputPort {

  private final FindClientesFromIdListOutputPort findClientesFromIdListOutputPort;

  @Override
  public List<Cliente> find(List<String> clienteIds) {
    return findClientesFromIdListOutputPort.find(clienteIds);
  }
}
