package br.ufpr.core.usecases;

import br.ufpr.core.domain.ApprovePendingContaInputData;
import br.ufpr.core.domain.ApprovedContaEvent;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.ApprovePendingContaInputPort;
import br.ufpr.core.ports.output.FindContaByIdOutputPort;
import br.ufpr.core.ports.output.FindContaByNumeroContaOutputPort;
import br.ufpr.core.ports.output.PublishContaAprovadaEventOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.model.enumerator.StatusConta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ApprovePendingContaUsecase implements ApprovePendingContaInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindContaByNumeroContaOutputPort findContaByNumeroContaOutputPort;
  private final FindContaByIdOutputPort findContaByIdOutputPort;
  private final PublishContaAprovadaEventOutputPort publishContaAprovadaEventOutputPort;

  public void execute(ApprovePendingContaInputData inputData){

    System.out.println("Aprovando conta do usuário");

    Conta conta = findContaByIdOutputPort.find(inputData.getContaId());

    validateConta(conta);

    Date today = new Date();

    String numeroConta = generateValidFourDigitsNumeroConta();

    BigDecimal saldo = BigDecimal.ZERO;
    BigDecimal limite = calculateLimiteContaBasedOnSalary(inputData.getClienteSalario());

    conta.setSaldo(saldo);
    conta.setLimite(limite);
    conta.setDataDecisao(today);
    conta.setNumeroConta(numeroConta);
    conta.setStatusConta(StatusConta.CONTA_APROVADA);

    saveContaOutputPort.save(conta);

    publishContaAprovadaEventOutputPort.publish(new ApprovedContaEvent(conta.getClienteId()));

    System.out.println("Conta aprovada com sucesso!");

  }


  private String generateValidFourDigitsNumeroConta(){

    int fourRandomDigits= new Random().nextInt(9000) + 1000;

    while (findContaByNumeroContaOutputPort.exists(fourRandomDigits)){
      fourRandomDigits = new Random().nextInt(9000) + 1000;
    }

    return String.valueOf(fourRandomDigits);
  }

  private BigDecimal calculateLimiteContaBasedOnSalary(BigDecimal salary){

    BigDecimal minimumSalaryAllowedValue = new BigDecimal(2000);

    boolean minimumSalaryValueReached = salary.compareTo(minimumSalaryAllowedValue) > 0;

    if(!minimumSalaryValueReached){

      return new BigDecimal(0);
    }

    BigDecimal limite = salary.divide(new BigDecimal(2));

    return limite;

  }

  private void validateConta(Conta conta){

    if(conta == null){
      throw new RuntimeException("Nenhuma conta encontrada");
    }

    if(!conta.getStatusConta().equals(StatusConta.CONTA_PENDENTE)){
      throw new RuntimeException("Aprovação/Recusa de conta só pode acontecer apenas uma vez");
    }
  }

}
