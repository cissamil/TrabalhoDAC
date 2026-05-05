package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.ClienteRequestInputData;
import br.ufpr.core.domain.ClienteResponseOutputData;
import br.ufpr.core.ports.input.GetClienteInputPort;
import br.ufpr.core.ports.output.FindClienteByClienteIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetClienteUseCase implements GetClienteInputPort {

  private final FindClienteByClienteIdOutputPort findClienteByClienteIdOutputPort;

  @Override
  public Cliente get(ClienteRequestInputData inputData) {

    String clienteId = inputData.getClienteId();

    System.out.println("Procurando cliente com id: " + clienteId);

    return findClienteByClienteIdOutputPort.find(clienteId);

  }
}
