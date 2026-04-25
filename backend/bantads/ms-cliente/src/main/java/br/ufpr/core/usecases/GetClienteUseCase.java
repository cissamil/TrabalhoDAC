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

  public FindClienteByClienteIdOutputPort findClienteByClienteIdOutputPort;

  @Override
  public Cliente get(ClienteRequestInputData inputData) {

    ClienteResponseOutputData outputData = new ClienteResponseOutputData();

    String clienteId = inputData.getClienteId();

    Cliente cliente = findClienteByClienteIdOutputPort.find(clienteId);

    if(cliente == null){
      throw new RuntimeException("Usuário não encontrado");
    }


    return cliente;
  }
}
