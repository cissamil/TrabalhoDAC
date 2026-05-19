package br.ufpr.core.ports.input;

import br.ufpr.core.domain.AssignGerenteToContaInputData;

public interface AssignNewGerenteToContaInputPort {

  void execute(AssignGerenteToContaInputData inputData);
}
