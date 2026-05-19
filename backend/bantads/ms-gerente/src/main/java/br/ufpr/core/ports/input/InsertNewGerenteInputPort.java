package br.ufpr.core.ports.input;

import br.ufpr.core.domain.Gerente;
import br.ufpr.core.domain.GerenteInputData;

public interface InsertNewGerenteInputPort {

  void execute(GerenteInputData inputData);
}
