package br.ufpr.core.usecases;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.RefusePendingContaInputData;
import br.ufpr.core.ports.input.RefusePendingContaInputPort;
import br.ufpr.core.ports.output.*;
import br.ufpr.core.domain.StatusConta;
import br.ufpr.infrastructure.exceptions.BusinessRuleException;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefusePendingContaUseCase implements RefusePendingContaInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindContaByIdOutputPort findContaByIdOutputPort;
  private final PublishContaRefusedEventOutputPort publishContaRefusedEventOutputPort;

  @Override
  public void execute(RefusePendingContaInputData inputData) {

    Integer contaId = inputData.getContaId();

    Conta conta = findContaByIdOutputPort.find(contaId);

    validateConta(conta);

    String clienteId = conta.getClienteId();
    String motivoRecusa = inputData.getMotivoRecusa();

    conta.setStatusConta(StatusConta.CONTA_REJEITADA);
    conta.setMotivoRecusa(motivoRecusa);

    saveContaOutputPort.save(conta);

    String content = "Viemos informar que, infelizmente, " +
                    "sua conta não foi aprovada no processo de avaliação de" +
                    " contas por um dos nossos gerentes" +
                    "\n\n Motivo da rejeição: " + motivoRecusa;

    publishContaRefusedEventOutputPort.publish(clienteId, content);

    System.out.println("Conta recusada com sucesso");

  }

  private void validateConta(Conta conta){
    RuntimeException runtimeException = null ;

    if(conta == null){
      throw new ResourceNotFoundException("Conta não encontrada");
    }

    if(!conta.getStatusConta().equals(StatusConta.CONTA_PENDENTE)){

      throw new BusinessRuleException("Aprovação/Recusa de conta só pode acontecer apenas uma vez");
    }

  }

}
