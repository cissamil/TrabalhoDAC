package br.ufpr.core.ports.input;

import br.ufpr.core.domain.BatchSyncContaInputData;
import br.ufpr.core.domain.SyncContaInputData;

public interface BatchSyncContaInputPort {
  void execute (BatchSyncContaInputData inputData);
}
