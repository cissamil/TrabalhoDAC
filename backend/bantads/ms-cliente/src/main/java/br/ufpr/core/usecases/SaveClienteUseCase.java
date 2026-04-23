package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.input.SaveClienteInputPort;
import br.ufpr.core.ports.output.FindClienteByClienteIdOutputPort;
import br.ufpr.core.ports.output.FindClienteByCpfOutputPort;
import br.ufpr.core.ports.output.SaveClienteOutputPort;
import br.ufpr.core.ports.output.SendCreateContaSagaOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SaveClienteUseCase implements SaveClienteInputPort {

  private final SaveClienteOutputPort saveClienteOutputPort;
  private final FindClienteByCpfOutputPort findClienteByCpfOutputPort;
  private final FindClienteByClienteIdOutputPort findClienteByClienteIdOutputPort;

  private final SendCreateContaSagaOutputPort sendCreateContaSagaOutputPort;

  @Override
  public void execute (Cliente cliente){

    validateCliente(cliente);

    String clienteId = generateValidClienteId();

    cliente.setClienteId(clienteId);

    saveClienteOutputPort.save(cliente);

    sendCreateContaSagaOutputPort.send(cliente);
  }

  private void validateCliente(Cliente cliente) {
    if (findClienteByCpfOutputPort.exists(cliente.getCpf())){
      throw new RuntimeException("CPF já cadastrado");
    }
  }

  private String generateValidClienteId() {

    String clienteId = UUID.randomUUID().toString();

    while(findClienteByClienteIdOutputPort.exists(clienteId)){

      clienteId = UUID.randomUUID().toString();
    }

    return clienteId;
  }

}
