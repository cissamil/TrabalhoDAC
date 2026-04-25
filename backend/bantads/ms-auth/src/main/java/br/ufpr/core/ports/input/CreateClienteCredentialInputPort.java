package br.ufpr.core.ports.input;

import br.ufpr.core.domain.TransferClienteIdInputData;

public interface CreateClienteCredentialInputPort {

  void execute(TransferClienteIdInputData inputData);

}
