package br.ufpr.core.ports.input;

import br.ufpr.core.domain.LoginInputData;

public interface LoginUserInputPort {

  String execute(LoginInputData inputData);
}
