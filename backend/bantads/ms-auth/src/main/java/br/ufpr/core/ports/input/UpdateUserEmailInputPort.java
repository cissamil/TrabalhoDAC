package br.ufpr.core.ports.input;

import br.ufpr.core.domain.UpdateUserEmailInputData;

public interface UpdateUserEmailInputPort {
  void execute(UpdateUserEmailInputData inputData);
}
