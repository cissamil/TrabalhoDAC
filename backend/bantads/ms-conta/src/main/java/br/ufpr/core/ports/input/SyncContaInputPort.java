package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Conta;
import br.ufpr.core.domain.Movimentacao;
import br.ufpr.core.domain.SyncContaInputData;

public interface SyncContaInputPort {
  void execute (SyncContaInputData inputData);
}
