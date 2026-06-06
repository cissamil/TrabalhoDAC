package br.ufpr.core.ports.input;

import br.ufpr.core.domain.TransferClienteDataInputData;

public interface RemoveClienteInputPort {

  void execute(TransferClienteDataInputData inputData);
}
