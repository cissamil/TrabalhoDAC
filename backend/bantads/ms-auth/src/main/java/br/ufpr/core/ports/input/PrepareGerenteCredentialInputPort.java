package br.ufpr.core.ports.input;

import br.ufpr.core.domain.GerenteInputData;

public interface PrepareGerenteCredentialInputPort {

  void execute(GerenteInputData inputData);
}
