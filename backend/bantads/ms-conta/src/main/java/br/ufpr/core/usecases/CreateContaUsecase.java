package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.TransferContaCreationDataInputData;
import br.ufpr.core.ports.input.CreateContaInputPort;
import br.ufpr.core.ports.output.FindContaByNumeroContaOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class CreateContaUsecase implements CreateContaInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindContaByNumeroContaOutputPort findContaByNumeroContaOutputPort;

  public void execute(TransferContaCreationDataInputData inputData){
    Conta novaConta = new Conta();

    Date today = new Date();
    BigDecimal saldo = BigDecimal.ZERO;
    Integer numeroConta = generateValidFourDigitsNumeroConta();
    BigDecimal limite = calculateLimiteContaBasedOnSalary(inputData.getSalario());

    novaConta.setId(null);
    novaConta.setSaldo(saldo);
    novaConta.setLimite(limite);
    novaConta.setDataCriacao(today);
    novaConta.setNumeroConta(numeroConta);
    novaConta.setClienteId(inputData.getClienteId());
    novaConta.setGerenteId(inputData.getGerenteId());

    saveContaOutputPort.save(novaConta);
  }

  private Integer generateValidFourDigitsNumeroConta(){

    Integer fourRandomDigits= new Random().nextInt(9000) + 1000;

    while (findContaByNumeroContaOutputPort.exists(fourRandomDigits)){
      fourRandomDigits = new Random().nextInt(9000) + 1000;
    }

    return fourRandomDigits;
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

}
