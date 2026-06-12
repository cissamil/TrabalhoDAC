package br.ufpr.core.ports.input;

import br.ufpr.core.domain.RemoveGerenteInputData;

public interface PrepareGerenteDeletionInputPort {
  void execute(RemoveGerenteInputData inputData);
}
