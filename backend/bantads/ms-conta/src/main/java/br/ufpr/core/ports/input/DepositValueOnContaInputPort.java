package br.ufpr.core.ports.input;

import br.ufpr.core.domain.DepositValueInputData;



public interface DepositValueOnContaInputPort {

  void execute(DepositValueInputData inputData);
}
