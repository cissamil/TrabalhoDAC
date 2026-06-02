package br.ufpr.core.usecases;

import br.ufpr.core.domain.ClienteOutputData;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.RefusePendingContaInputData;
import br.ufpr.core.ports.input.RefusePendingContaInputPort;
import br.ufpr.core.ports.output.ConsultClienteEmailOutputPort;
import br.ufpr.core.ports.output.FindContaByIdOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.core.domain.StatusConta;
import br.ufpr.core.ports.output.SendEmailOutputPort;
import infrastructure.exceptions.BusinessRuleException;
import infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefusePendingContaUseCase implements RefusePendingContaInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final SendEmailOutputPort sendEmailOutputPort;
  private final FindContaByIdOutputPort findContaByIdOutputPort;
  private final ConsultClienteEmailOutputPort consultClienteEmailOutputPort;

  @Override
  public void execute(RefusePendingContaInputData inputData) {

    Integer contaId = inputData.getContaId();

    Conta conta = findContaByIdOutputPort.find(contaId);

    validateConta(conta);

    String motivoRecusa = inputData.getMotivoRecusa();

    conta.setStatusConta(StatusConta.CONTA_REJEITADA);
    conta.setMotivoRecusa(motivoRecusa);

    saveContaOutputPort.save(conta);
    sendMotivoRecusaToClienteEmail(conta);

    System.out.println("Conta recusada com sucesso");

  }

  private void sendMotivoRecusaToClienteEmail(Conta conta) {
    ClienteOutputData outputData = consultClienteEmailOutputPort.consult(conta.getClienteId());

    String clienteEmail = outputData.getClienteEmail();
    String subject = "Sua conta bancária foi reprovada. Veja o motivo";
    String message =
      "Viemos informar que, infelizmente, " +
      "sua conta foi reprovada por um de nossos gerentes." +
      " \n Veja o motivo: \"" + conta.getMotivoRecusa() + "\"";

    sendEmailOutputPort.send(clienteEmail, subject, message);
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
