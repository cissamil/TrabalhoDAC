package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.ports.input.CreateContaInputPort;
import br.ufpr.core.ports.output.FindContaByNumeroContaOutputPort;
import br.ufpr.core.ports.output.FindGerenteIdWithFewerClientesOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.model.enumerator.StatusConta;
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
  private final FindGerenteIdWithFewerClientesOutputPort findGerenteIdWithFewerClientesOutputPort;

  public void execute(TransferClienteDataInputData inputData){

    System.out.println("Criando conta do usuário");

    Conta novaConta = new Conta();

    Date today = new Date();

    String numeroConta = generateValidFourDigitsNumeroConta();

    String clienteId = inputData.getClienteId();
    String gerenteId = findGerenteIdWithFewerClientesOutputPort.find();

    BigDecimal saldo = BigDecimal.ZERO;
    BigDecimal limite = calculateLimiteContaBasedOnSalary(inputData.getSalario());

    validateGerenteId(gerenteId);

    novaConta.setId(null);
    novaConta.setSaldo(saldo);
    novaConta.setLimite(limite);
    novaConta.setDataCriacao(today);
    novaConta.setNumeroConta(numeroConta);
    novaConta.setClienteId(clienteId);
    novaConta.setGerenteId(gerenteId);
    novaConta.setStatusConta(StatusConta.CONTA_PENDENTE);

    saveContaOutputPort.save(novaConta);
  }

  private static void validateGerenteId(String gerenteId) {

    System.out.println("Id do gerente atribuido: " + gerenteId);
    if(gerenteId == null) {
      throw new RuntimeException("Gerente não encontrado");
    }
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

}
