package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.input.RemoveClienteInputPort;
import br.ufpr.core.ports.output.FindClienteByClienteIdOutputPort;
import br.ufpr.core.ports.output.PublishClienteNotificationReadyOutputPort;
import br.ufpr.core.ports.output.PublishEmailNotificationEventOutputPort;
import br.ufpr.core.ports.output.RemoveClienteOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveClienteUseCase implements RemoveClienteInputPort {

  private final RemoveClienteOutputPort removeClienteOutputPort;
  private final FindClienteByClienteIdOutputPort findClienteByClienteIdOutputPort;
  private final PublishEmailNotificationEventOutputPort publishEmailNotificationEventOutputPort;

  @Override
  public void execute(TransferClienteDataInputData inputData) {

    String clienteId = inputData.getClienteId();

    Cliente cliente = findClienteByClienteIdOutputPort.find(clienteId);

    if(cliente == null){
      System.out.println("Aviso: O cliente " + clienteId + " já não existe no banco. Abortando...");
      return;
    }

    System.out.println("Cliente encontrado: " + cliente);

    removeClienteOutputPort.remove(cliente);

    String clienteEmail = cliente.getEmail();
    String subject = "Erro ao cadastrar a sua conta";
    String content = "Infelizmente tivemos um erro ao realizar o cadastro da sua conta. Por favor, realize o cadastro novamente";

    publishEmailNotificationEventOutputPort.publish(clienteEmail, clienteId, content, subject);
  }
}
