package br.ufpr.core.ports.input;

import br.ufpr.core.domain.WithdrawValueInputData;

public interface WithDrawValueOfContaInputPort {

  void execute(WithdrawValueInputData inputData);

}
