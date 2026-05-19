package br.ufpr.core.ports.output;

import br.ufpr.core.domain.TransferContasToGerenteInputData;

public interface TransferContasToNewGerenteInputPort {

  void execute(TransferContasToGerenteInputData inputData);
}
