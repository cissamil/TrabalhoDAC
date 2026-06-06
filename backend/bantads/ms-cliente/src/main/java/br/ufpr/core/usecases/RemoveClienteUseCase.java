package br.ufpr.core.usecases;

import br.ufpr.core.domain.Cliente;
import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.input.RemoveClienteInputPort;
import br.ufpr.core.ports.output.FindClienteByClienteIdOutputPort;
import br.ufpr.core.ports.output.RemoveClienteOutputPort;
import br.ufpr.core.ports.output.SendEmailOutputPort;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveClienteUseCase implements RemoveClienteInputPort {

  private final SendEmailOutputPort sendEmailOutputPort;
  private final RemoveClienteOutputPort removeClienteOutputPort;
  private final FindClienteByClienteIdOutputPort findClienteByClienteIdOutputPort;
  @Override
  public void execute(TransferClienteDataInputData inputData) {

    Cliente cliente = findClienteByClienteIdOutputPort.find(inputData.getClienteId());

    if(cliente == null){
      System.out.println("Aviso: O cliente " + inputData.getClienteId() + " já não existe no banco. Abortando...");
      return;
    }

    System.out.println("Cliente encontrado: " + cliente);

    removeClienteOutputPort.remove(cliente);

    sendEmailOutputPort.send(
      cliente.getEmail(),
      "Erro ao cadastrar sua conta",
      "Houve um erro durante o cadastro da sua conta. Por favor, realize o cadastro novamente"
    );
  }
}
