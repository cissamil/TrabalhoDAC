package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.domain.TipoMovimentacao;
import br.ufpr.core.domain.TransferValueInputData;
import br.ufpr.core.ports.input.TransferMoneyToAnotherContaInputPort;
import br.ufpr.core.ports.output.FindContaByNumeroContaOutputPort;
import br.ufpr.core.ports.output.RegisterNewMovimentacaoOutputPort;
import br.ufpr.core.ports.output.SaveContaOutputPort;
import br.ufpr.infrastructure.exceptions.BusinessRuleException;
import br.ufpr.infrastructure.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferMoneyToAnotherContaUseCase implements TransferMoneyToAnotherContaInputPort{

  private final SaveContaOutputPort saveContaOutputPort;
  private final FindContaByNumeroContaOutputPort findContaByNumeroContaOutputPort;
  private final RegisterNewMovimentacaoOutputPort registerNewMovimentacaoOutputPort;

  @Override
  @Transactional
  public void execute(TransferValueInputData inputData) {

      Conta contaToWithdraw = findContaByNumeroContaOutputPort.find(inputData.getOriginContaNumber());
      Conta contaToTransfer = findContaByNumeroContaOutputPort.find(inputData.getDestinyContaNumber());

      validateContas(contaToWithdraw, contaToTransfer);

      BigDecimal contaToWithdrawSaldo = contaToWithdraw.getSaldo();
      BigDecimal valueToTransfer = inputData.getValue();


      BigDecimal contaToTransferSaldo = contaToTransfer.getSaldo();

      validateWithdrawAvailability(contaToWithdrawSaldo, valueToTransfer);

      BigDecimal contaToWithdrawNewSaldo = contaToWithdrawSaldo.subtract(valueToTransfer);
      BigDecimal contaToTransferNewSaldo = contaToTransferSaldo.add(valueToTransfer);

      contaToWithdraw.setSaldo(contaToWithdrawNewSaldo);
      contaToTransfer.setSaldo(contaToTransferNewSaldo);

      saveContaOutputPort.save(contaToWithdraw);
      saveContaOutputPort.save(contaToTransfer);
      registerMovimentacao(contaToWithdraw, contaToTransfer, valueToTransfer);
  }

  private void validateContas(Conta contaToWithdraw, Conta contaToTransfer) {
    if (contaToWithdraw == null || contaToTransfer == null){
      throw new ResourceNotFoundException("Contas não encontradas para a transferencia");
    }
  }

  private void registerMovimentacao(Conta contaOrigin, Conta contaDestiny, BigDecimal valueToTransfer) {
    Movimentacao movimentacao = new Movimentacao();

    movimentacao.setId(null);
    movimentacao.setDataHora(new Date());
    movimentacao.setMovimentacaoId(UUID.randomUUID().toString());
    movimentacao.setClienteOrigemId(contaOrigin.getClienteId());
    movimentacao.setClienteDestinoId(contaDestiny.getClienteId());
    movimentacao.setValor(valueToTransfer);
    movimentacao.setTipoMovimentacao(TipoMovimentacao.TRANSFERENCIA);

    registerNewMovimentacaoOutputPort.register(movimentacao);
  }


  private void validateWithdrawAvailability(BigDecimal saldo, BigDecimal valueToWithdraw) {
    boolean valueToWithdrawIsGreaterThanAvailableSaldo = valueToWithdraw.compareTo(saldo) > 0;

    if(valueToWithdrawIsGreaterThanAvailableSaldo){
      throw new BusinessRuleException("Saldo insuficiente");
    }
  }

}
