package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.domain.TipoMovimentacao;
import br.ufpr.core.domain.WithdrawValueInputData;
import br.ufpr.core.ports.input.WithDrawValueOfContaInputPort;
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
public class WithdrawValueOfContaUseCase implements WithDrawValueOfContaInputPort {


  private final SaveContaOutputPort saveContaOutputPort;
  private final FindContaByNumeroContaOutputPort findContaByNumeroContaOutputPort;
  private final RegisterNewMovimentacaoOutputPort registerNewMovimentacaoOutputPort;

  @Override
  public void execute(WithdrawValueInputData inputData) {

    Conta contaToWithdraw = findContaByNumeroContaOutputPort.find(inputData.getContaNumber());

    validateContaToWithdraw(contaToWithdraw);

    BigDecimal saldo = contaToWithdraw.getSaldo();
    BigDecimal valueToWithdraw = inputData.getValue();

    validateWithdrawAvailability(valueToWithdraw, saldo);

    BigDecimal newSaldo = saldo.subtract(inputData.getValue());

    contaToWithdraw.setSaldo(newSaldo);

    saveContaOutputPort.save(contaToWithdraw);
    registerMovimentacao(contaToWithdraw, valueToWithdraw);

  }

  private static void validateWithdrawAvailability(BigDecimal valueToWithdraw, BigDecimal saldo) {
    boolean valueToWithdrawIsGreaterThanAvailableSaldo = valueToWithdraw.compareTo(saldo) > 0;

    if(valueToWithdrawIsGreaterThanAvailableSaldo){
      throw new RuntimeException("Saldo insuficiente");
    }
  }

  private void registerMovimentacao(Conta contaToDeposit, BigDecimal valueToWithdraw) {
    Movimentacao movimentacao = new Movimentacao();

    movimentacao.setId(null);
    movimentacao.setDataHora(new Date());
    movimentacao.setMovimentacaoId(UUID.randomUUID().toString());
    movimentacao.setClienteOrigemId(contaToDeposit.getClienteId());
    movimentacao.setClienteDestinoId(null);
    movimentacao.setValor(valueToWithdraw);
    movimentacao.setTipoMovimentacao(TipoMovimentacao.SAQUE);

    registerNewMovimentacaoOutputPort.register(movimentacao);
  }


  private static void validateContaToWithdraw(Conta contaToWithdraw) {
    if(contaToWithdraw == null){
      throw new RuntimeException("Conta não encontrada");
    }
  }


}
