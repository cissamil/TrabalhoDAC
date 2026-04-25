package br.ufpr.core.ports.input;

import br.ufpr.core.domain.TransferClienteDataInputData;
import br.ufpr.core.domain.TransferContaCreationDataInputData;

public interface CreateContaInputPort {

  void execute(TransferClienteDataInputData inputData);
}
