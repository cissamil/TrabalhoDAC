package br.ufpr.core.ports.input;

import br.ufpr.core.domain.TransferClienteDataInputData;

public interface CreatePendingContaInputPort {

  void execute(TransferClienteDataInputData inputData);
}
