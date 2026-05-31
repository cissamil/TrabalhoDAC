package br.ufpr.core.ports.input;

import br.ufpr.core.domain.RemoveGerenteInputData;

public interface RemoveGerenteInputPort {
  void execute(RemoveGerenteInputData inputData);
}
