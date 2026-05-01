package br.ufpr.core.ports.input;

import br.ufpr.core.domain.ApprovePendingContaInputData;

public interface ApprovePendingContaInputPort {

  void execute(ApprovePendingContaInputData inputData);
}
