package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.ports.input.RevertClienteEmailInputPort;
import br.ufpr.core.ports.output.FindClienteByClienteIdOutputPort;
import br.ufpr.core.ports.output.PublishClienteNotificationReadyOutputPort;
import br.ufpr.core.ports.output.PublishEmailNotificationEventOutputPort;
import br.ufpr.core.ports.output.SaveClienteOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RevertClienteEmailUseCase implements RevertClienteEmailInputPort {

  private final SaveClienteOutputPort saveClienteOutputPort;
  private final FindClienteByClienteIdOutputPort findClienteByClienteIdOutputPort;
  private final PublishEmailNotificationEventOutputPort publishEmailNotificationEventOutputPort;

  @Override
  public void execute(String clienteId, String previousEmail) {


    Cliente cliente = findClienteByClienteIdOutputPort.find(clienteId);

    if (cliente == null) {
      System.out.println("Cliente não encontrado. Abortando...");
      return;
    }

    if(cliente.getEmail().equals(previousEmail)){
      System.out.println("Email do cliente já foi atualizado. Abortando...");
      return;
    }

    String notificationSubject = "Erro ao atualizar seu email";
    String notificationContent = "Houve um erro ao atualizar seu email, por favor, logue com o seu email antigo e tente novamente";

    cliente.setEmail(previousEmail);

    saveClienteOutputPort.save(cliente);
    publishEmailNotificationEventOutputPort.publish(previousEmail, clienteId,notificationContent, notificationSubject);
  }
}
