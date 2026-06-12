package br.ufpr.core.ports.input;

import br.ufpr.core.domain.NotifyClienteInputData;

public interface PrepareClienteNotificationInputPort {

  void execute(NotifyClienteInputData inputData);
}
