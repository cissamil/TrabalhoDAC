package br.ufpr.core.ports;

import br.ufpr.core.domain.SendEmailInputData;

public interface SendEmailInputPort {

  void send(SendEmailInputData inputData);
}
