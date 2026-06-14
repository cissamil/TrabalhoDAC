package br.ufpr.core.usecases;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.domain.SyncContaInputData;
import br.ufpr.core.ports.input.SyncContaInputPort;
import br.ufpr.core.ports.output.FindContaByContaIdOutputPort;
import br.ufpr.core.ports.output.SyncContaOutputPort;
import br.ufpr.core.ports.output.SyncMovimentacaoOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SyncContaUseCase implements SyncContaInputPort {

  private final SyncContaOutputPort syncContaOutputPort;
  private final SyncMovimentacaoOutputPort syncMovimentacaoOutputPort;
  private final FindContaByContaIdOutputPort findContaByContaIdOutputPort;

  @Override
  public void execute(SyncContaInputData inputData) {

    Conta receivedConta = inputData.getConta();

    if(receivedConta == null){
      System.out.println("Conta nula. Abortando...");
      return;
    }

    Conta existingConta = findContaByContaIdOutputPort.find(receivedConta.getContaId());

    System.out.println("Conta Recebida: " + receivedConta);
    System.out.println("Conta existente: " + existingConta);

    if(existingConta == null){

      System.out.println("Nova conta para criar");

      receivedConta.setId(null);
    } else{

      System.out.println("Conta para atualizar");

      receivedConta.setId(existingConta.getId());
    }

    Movimentacao movimentacao = inputData.getMovimentacao();


    syncContaOutputPort.sync(receivedConta);

    if (movimentacao != null) {

      syncMovimentacaoOutputPort.sync(movimentacao);

    }

  }
}
