package br.ufpr.core.usecases;

import br.ufpr.core.domain.GerenteInputData;
import br.ufpr.core.domain.TipoUsuario;
import br.ufpr.core.domain.UserInputData;
import br.ufpr.core.ports.input.CreateUserCredentialInputPort;
import br.ufpr.core.ports.input.PrepareGerenteCredentialInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrepareGerenteCredentialUseCase implements PrepareGerenteCredentialInputPort {

  private final CreateUserCredentialInputPort createUserCredentialInputPort;

  @Override
  public void execute(GerenteInputData inputData) {

    UserInputData userInputData = new UserInputData();

    userInputData.setUserId(inputData.getGerenteId());
    userInputData.setEmail(inputData.getEmail());
    userInputData.setTipoUsuario(TipoUsuario.GERENTE);
    userInputData.setSenha(inputData.getSenha());

    createUserCredentialInputPort.execute(userInputData);

  }
}
