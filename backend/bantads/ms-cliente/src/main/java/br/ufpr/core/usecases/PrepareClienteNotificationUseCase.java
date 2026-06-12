package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.NotifyClienteInputData;
import br.ufpr.core.ports.input.PrepareClienteNotificationInputPort;
import br.ufpr.core.ports.output.FindClienteByClienteIdOutputPort;
import br.ufpr.core.ports.output.PublishClienteNotificationReadyOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrepareClienteNotificationUseCase implements PrepareClienteNotificationInputPort {

  private final FindClienteByClienteIdOutputPort findClienteByClienteIdOutputPort;
  private final PublishClienteNotificationReadyOutputPort publishClienteNotificationReadyOutputPort;

  @Override
  public void execute(NotifyClienteInputData inputData) {

    String clienteId = inputData.getClienteId();
    String message = inputData.getMessage();

    Cliente cliente = findClienteByClienteIdOutputPort.find(clienteId);

    validateCliente(cliente);

    String email = cliente.getEmail();

    publishClienteNotificationReadyOutputPort.publish(email, clienteId, message);
  }

  private static void validateCliente(Cliente cliente) {
    if (cliente == null) {

      System.out.println("Cliente não cadastrado no banco. Abortando...");
      return;
    }
  }
}
