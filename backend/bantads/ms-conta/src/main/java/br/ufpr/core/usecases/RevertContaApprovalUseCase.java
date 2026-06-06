package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.StatusConta;
import br.ufpr.core.ports.input.RevertContaApprovalInputPort;
import br.ufpr.core.ports.output.FindContaByClienteIdOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RevertContaApprovalUseCase implements RevertContaApprovalInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindContaByClienteIdOutputPort findContaByClienteIdOutputPort;

  @Override
  public void execute(String clienteId) {

    Conta conta = findContaByClienteIdOutputPort.find(clienteId);

    if(conta.getStatusConta() == StatusConta.CONTA_PENDENTE){
      System.out.println("Status da conta já foi revertido. Abortando...");
    }

    conta.setSaldo(null);
    conta.setLimite(null);
    conta.setDataDecisao(null);
    conta.setNumeroConta(null);
    conta.setMotivoRecusa(null);
    conta.setStatusConta(StatusConta.CONTA_PENDENTE);

    Conta updatedConta = saveContaOutputPort.save(conta);

    System.out.println("Conta " + updatedConta.getContaId() + " revertida com sucesso");
  }
}
