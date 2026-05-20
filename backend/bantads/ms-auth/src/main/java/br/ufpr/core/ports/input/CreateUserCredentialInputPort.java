package br.ufpr.core.ports.input;

import br.ufpr.core.domain.UserInputData;

public interface CreateUserCredentialInputPort {

  void execute(UserInputData inputData);
}
