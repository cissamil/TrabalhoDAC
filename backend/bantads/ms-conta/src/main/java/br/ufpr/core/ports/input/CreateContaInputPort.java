package br.ufpr.core.ports.input;

import br.ufpr.core.domain.TransferContaCreationDataInputData;

public interface CreateContaInputPort {

  void execute(TransferContaCreationDataInputData inputData);
}
