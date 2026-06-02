package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.UpdateContaLimitInputData;
import br.ufpr.core.ports.input.UpdateContaLimitInputPort;
import br.ufpr.core.ports.output.FindContaByClienteIdOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class UpdateContaLimitUseCase implements UpdateContaLimitInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindContaByClienteIdOutputPort findContaByClienteIdOutputPort;

  @Override
  public void execute(UpdateContaLimitInputData inputData) {

    String clienteId = inputData.getClienteId();
    BigDecimal clienteSalary = inputData.getClienteSalary();

    Conta conta = findContaByClienteIdOutputPort.find(clienteId);

    validateConta(conta);

    System.out.println("Conta: " + conta);

    BigDecimal newLimit = generateNewLimit(conta, clienteSalary);

    conta.setLimite(newLimit);

    saveContaOutputPort.save(conta);

  }

  private BigDecimal generateNewLimit(Conta conta, BigDecimal clienteSalary) {

    BigDecimal actualBalance = conta.getSaldo();

    boolean isBalanceNegative = actualBalance.compareTo(new BigDecimal(BigInteger.ZERO)) < 0;
    boolean isValidSalary = clienteSalary.compareTo(new BigDecimal(2000)) >= 0;

    if(isBalanceNegative){
      return actualBalance.multiply(new BigDecimal(-1));
    }

    if(isValidSalary){
      return clienteSalary.divide(new BigDecimal(2), 2, RoundingMode.HALF_EVEN);
    }

    return new BigDecimal(BigInteger.ZERO);
  }

  private void validateConta(Conta conta) {
    if(conta == null){
      throw new ResourceNotFoundException("Conta não encontrada");
    }
  }
}
