package br.ufpr.core.ports.input;

import br.ufpr.core.domain.TransferValueInputData;

public interface TransferMoneyToAnotherContaInputPort {

  void execute(TransferValueInputData inputData);
}
