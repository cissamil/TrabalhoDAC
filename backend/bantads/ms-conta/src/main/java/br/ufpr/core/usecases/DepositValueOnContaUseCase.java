package br.ufpr.core.usecases;

import br.ufpr.core.domain.*;
import br.ufpr.core.ports.input.DepositValueOnContaInputPort;
import br.ufpr.core.ports.output.FindContaByNumeroContaOutputPort;
import br.ufpr.core.ports.output.RegisterNewMovimentacaoOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DepositValueOnContaUseCase implements DepositValueOnContaInputPort {

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindContaByNumeroContaOutputPort findContaByNumeroContaOutputPort;
  private final RegisterNewMovimentacaoOutputPort registerNewMovimentacaoOutputPort;

  @Override
  public void execute(DepositValueInputData inputData) {
    Conta contaToDeposit = findContaByNumeroContaOutputPort.find(inputData.getContaNumber());

    validateContaToDeposit(contaToDeposit);

    BigDecimal saldo = contaToDeposit.getSaldo();
    BigDecimal valueToDeposit = inputData.getValue();

    BigDecimal newSaldo = saldo.add(valueToDeposit);

    contaToDeposit.setSaldo(newSaldo);


    saveContaOutputPort.save(contaToDeposit);

    registerMovimentacao(contaToDeposit, valueToDeposit);

  }

  private void registerMovimentacao(Conta contaToDeposit, BigDecimal valueToDeposit) {
    Movimentacao movimentacao = new Movimentacao();

    movimentacao.setId(null);
    movimentacao.setDataHora(new Date());
    movimentacao.setMovimentacaoId(UUID.randomUUID().toString());
    movimentacao.setClienteOrigemId(contaToDeposit.getClienteId());
    movimentacao.setClienteDestinoId(null);
    movimentacao.setValor(valueToDeposit);
    movimentacao.setTipoMovimentacao(TipoMovimentacao.DEPOSITO);

    registerNewMovimentacaoOutputPort.register(movimentacao);
  }


  private void validateContaToDeposit(Conta contaToDeposit) {
    if(contaToDeposit == null){
      throw new RuntimeException("Conta não encontrada");
    }
  }
}
