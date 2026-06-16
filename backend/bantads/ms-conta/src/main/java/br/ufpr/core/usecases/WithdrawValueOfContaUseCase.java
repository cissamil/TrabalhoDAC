package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.domain.TipoMovimentacao;
import br.ufpr.core.domain.WithdrawValueInputData;
import br.ufpr.core.ports.input.WithDrawValueOfContaInputPort;
import br.ufpr.core.ports.output.FindContaByNumeroContaOutputPort;
import br.ufpr.core.ports.output.PublishSyncContaEventOutputPort;
import br.ufpr.core.ports.output.RegisterNewMovimentacaoOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.infrastructure.exceptions.BusinessRuleException;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WithdrawValueOfContaUseCase implements WithDrawValueOfContaInputPort {


  private final SaveContaOutputPort saveContaOutputPort;
  private final PublishSyncContaEventOutputPort publishSyncContaEventOutputPort;
  private final FindContaByNumeroContaOutputPort findContaByNumeroContaOutputPort;
  private final RegisterNewMovimentacaoOutputPort registerNewMovimentacaoOutputPort;

  @Override
  public void execute(WithdrawValueInputData inputData) {

    Conta contaToWithdraw = findContaByNumeroContaOutputPort.find(inputData.getContaNumber());

    validateContaToWithdraw(contaToWithdraw);

    BigDecimal saldo = contaToWithdraw.getSaldo();
    BigDecimal limite = contaToWithdraw.getLimite();
    BigDecimal valueToWithdraw = inputData.getValue();

    validateWithdrawAvailability(valueToWithdraw, saldo, limite);

    BigDecimal newSaldo = saldo.subtract(inputData.getValue());

    contaToWithdraw.setSaldo(newSaldo);

    saveContaOutputPort.save(contaToWithdraw);
    Movimentacao movimentacao = registerMovimentacao(contaToWithdraw, valueToWithdraw);

    publishSyncContaEventOutputPort.publish(contaToWithdraw, movimentacao);

  }

  private static void validateWithdrawAvailability(BigDecimal valueToWithdraw, BigDecimal saldo, BigDecimal limite) {

    BigDecimal saldoReal = saldo.add(limite);

    boolean valueToWithdrawIsGreaterThanAvailableSaldo = valueToWithdraw.compareTo(saldoReal) > 0;

    if(valueToWithdrawIsGreaterThanAvailableSaldo){
      throw new BusinessRuleException("Saldo insuficiente");
    }
  }

  private Movimentacao registerMovimentacao(Conta contaToDeposit, BigDecimal valueToWithdraw) {
    Movimentacao movimentacao = new Movimentacao();

    movimentacao.setId(null);
    movimentacao.setDataHora(new Date());
    movimentacao.setMovimentacaoId(UUID.randomUUID().toString());
    movimentacao.setClienteOrigemId(contaToDeposit.getClienteId());
    movimentacao.setClienteDestinoId(null);
    movimentacao.setValor(valueToWithdraw);
    movimentacao.setTipoMovimentacao(TipoMovimentacao.SAQUE);

    return registerNewMovimentacaoOutputPort.register(movimentacao);
  }


  private static void validateContaToWithdraw(Conta contaToWithdraw) {
    if(contaToWithdraw == null){
      throw new ResourceNotFoundException("Conta não encontrada");
    }
  }


}
