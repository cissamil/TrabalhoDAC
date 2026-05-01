package br.ufpr.core.ports.input;

import br.ufpr.core.domain.AprovePendingContaInputData;
import br.ufpr.core.domain.TransferClienteDataInputData;

public interface AprovePendingContaInputPort {

  void execute(AprovePendingContaInputData inputData);
}
