package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.RefusePendingContaInputData;
import br.ufpr.core.ports.input.RefusePendingContaInputPort;
import br.ufpr.core.ports.output.FindContaByIdOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.core.domain.StatusConta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefusePendingContaUseCase implements RefusePendingContaInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindContaByIdOutputPort findContaByIdOutputPort;

  @Override
  public void execute(RefusePendingContaInputData inputData) {

    Integer contaId = inputData.getContaId();

    Conta conta = findContaByIdOutputPort.find(contaId);

    validateConta(conta);

    String motivoRecusa = inputData.getMotivoRecusa();

    conta.setStatusConta(StatusConta.CONTA_REJEITADA);
    conta.setMotivoRecusa(motivoRecusa);

    saveContaOutputPort.save(conta);

    System.out.println("Conta recusada com sucesso");

  }


  private void validateConta(Conta conta){
    RuntimeException runtimeException = null ;

    if(conta == null){
      throw new RuntimeException("Conta não encontrada");
    }

    if(!conta.getStatusConta().equals(StatusConta.CONTA_PENDENTE)){

      throw new RuntimeException("Aprovação/Recusa de conta só pode acontecer apenas uma vez");
    }

  }

}
