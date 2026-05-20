package br.ufpr.core.ports.input;

import br.ufpr.core.domain.TransferClienteIdInputData;

public interface PrepareClienteCredentialInputPort {

  void execute(TransferClienteIdInputData inputData);

}
