package br.ufpr.core.usecases;

import br.ufpr.core.domain.BatchSyncContaInputData;
import br.ufpr.core.domain.Conta;
import br.ufpr.core.ports.input.BatchSyncContaInputPort;
import br.ufpr.core.ports.output.BatchSyncContaOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchSyncContaUseCase implements BatchSyncContaInputPort {

  private final BatchSyncContaOutputPort batchSyncContaOutputPort;

  @Override
  public void execute(BatchSyncContaInputData inputData) {

    List<Conta> contas = inputData.getContas();

    if (contas.isEmpty()) {

      System.out.println("Nenhuma conta a sincronizar. Abortando...");
      return;
    }

    batchSyncContaOutputPort.sync(contas);
  }
}
